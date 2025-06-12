package com.urban.EmployeeManager.dto;

import com.urban.EmployeeManager.enums.WorkType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleCreateDTO {
    @NotNull(message = "ID nhân viên không được để trống")
    private Long employeeId;

    @NotNull(message = "Ngày làm việc không được để trống")
    private LocalDate workDate;

    private LocalTime startTime;
    private LocalTime endTime;

    @NotNull(message = "Loại công việc không được để trống")
    private WorkType workType;

    private String notes;
}

