package com.urban.EmployeeManager.service;

import com.urban.EmployeeManager.dto.ScheduleCreateDTO;
import com.urban.EmployeeManager.dto.ScheduleDTO;
import com.urban.EmployeeManager.dto.ScheduleUpdateDTO;
import com.urban.EmployeeManager.enums.WorkType;
import com.urban.EmployeeManager.exception.ResourceNotFoundException;
import com.urban.EmployeeManager.exception.InvalidScheduleException;
import com.urban.EmployeeManager.model.Employee;
import com.urban.EmployeeManager.model.Schedule;
import com.urban.EmployeeManager.repository.EmployeeRepository;
import com.urban.EmployeeManager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    public List<ScheduleDTO> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate,
                                                     Long officeId, WorkType workType) {
        List<Schedule> schedules = scheduleRepository.findByFilters(startDate, endDate, officeId, workType);
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesByEmployee(Long employeeId, LocalDate startDate, LocalDate endDate) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + employeeId);
        }

        List<Schedule> schedules = scheduleRepository.findByEmployeeIdAndDateRange(employeeId, startDate, endDate);
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ScheduleDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch làm việc với ID: " + id));
        return convertToDTO(schedule);
    }

    public ScheduleDTO createSchedule(ScheduleCreateDTO createDTO) {
        // Validate ngày không được ở quá khứ
        if (createDTO.getWorkDate().isBefore(LocalDate.now())) {
            throw new InvalidScheduleException("Không thể tạo lịch làm việc cho ngày đã qua");
        }

        // Validate thời gian
        if (createDTO.getStartTime() != null && createDTO.getEndTime() != null &&
                createDTO.getStartTime().isAfter(createDTO.getEndTime())) {
            throw new InvalidScheduleException("Thời gian bắt đầu không thể sau thời gian kết thúc");
        }

        Employee employee = employeeRepository.findById(createDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + createDTO.getEmployeeId()));

        Schedule schedule = new Schedule();
        schedule.setEmployee(employee);
        schedule.setWorkDate(createDTO.getWorkDate());
        schedule.setStartTime(createDTO.getStartTime());
        schedule.setEndTime(createDTO.getEndTime());
        schedule.setWorkType(createDTO.getWorkType());
        schedule.setNotes(createDTO.getNotes());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(savedSchedule);
    }

    public ScheduleDTO updateSchedule(Long id, ScheduleUpdateDTO updateDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch làm việc với ID: " + id));

        // Validate ngày không được ở quá khứ (trừ khi chỉ cập nhật notes)
        if (updateDTO.getWorkDate() != null && updateDTO.getWorkDate().isBefore(LocalDate.now())) {
            throw new InvalidScheduleException("Không thể cập nhật lịch làm việc cho ngày đã qua");
        }

        // Validate thời gian
        LocalTime startTime = updateDTO.getStartTime() != null ? updateDTO.getStartTime() : schedule.getStartTime();
        LocalTime endTime = updateDTO.getEndTime() != null ? updateDTO.getEndTime() : schedule.getEndTime();

        if (startTime != null && endTime != null && startTime.isAfter(endTime)) {
            throw new InvalidScheduleException("Thời gian bắt đầu không thể sau thời gian kết thúc");
        }

        // Cập nhật thông tin
        if (updateDTO.getWorkDate() != null) {
            schedule.setWorkDate(updateDTO.getWorkDate());
        }
        if (updateDTO.getStartTime() != null) {
            schedule.setStartTime(updateDTO.getStartTime());
        }
        if (updateDTO.getEndTime() != null) {
            schedule.setEndTime(updateDTO.getEndTime());
        }
        if (updateDTO.getWorkType() != null) {
            schedule.setWorkType(updateDTO.getWorkType());
        }
        if (updateDTO.getNotes() != null) {
            schedule.setNotes(updateDTO.getNotes());
        }

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(savedSchedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch làm việc với ID: " + id));

        // Không cho phép xóa lịch của ngày đã qua
        if (schedule.getWorkDate().isBefore(LocalDate.now())) {
            throw new InvalidScheduleException("Không thể xóa lịch làm việc của ngày đã qua");
        }

        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setEmployeeId(schedule.getEmployee().getId());
        dto.setEmployeeName(schedule.getEmployee().getName());
        dto.setWorkDate(schedule.getWorkDate());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        dto.setWorkType(schedule.getWorkType());
        dto.setNotes(schedule.getNotes());
        return dto;
    }
}

