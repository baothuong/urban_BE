// DataSeeder.java
package com.urban.EmployeeManager.service;

import com.urban.EmployeeManager.config.DataSeedingConfig;
import com.urban.EmployeeManager.enums.Gender;
import com.urban.EmployeeManager.enums.Position;
import com.urban.EmployeeManager.enums.Role;
import com.urban.EmployeeManager.enums.WorkType;
import com.urban.EmployeeManager.model.Employee;
import com.urban.EmployeeManager.model.Office;
import com.urban.EmployeeManager.model.Schedule;
import com.urban.EmployeeManager.repository.EmployeeRepository;
import com.urban.EmployeeManager.repository.OfficeRepository;
import com.urban.EmployeeManager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test") // Không chạy khi test
public class DataSeeder implements CommandLineRunner {

    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataSeedingConfig dataSeedingConfig;

    @Override
    public void run(String... args) throws Exception {
        if (!dataSeedingConfig.isSeedEnabled()) {
            log.info("Data seeding bị vô hiệu hóa trong cấu hình.");
            return;
        }

        if (shouldSeedData()) {
            log.info("Bắt đầu seed dữ liệu...");
            seedOffices();
            seedEmployees();
            seedSchedules();
            log.info("Hoàn thành seed dữ liệu!");
        } else {
            log.info("Dữ liệu đã tồn tại, bỏ qua seed.");
        }
    }

    private boolean shouldSeedData() {
        return officeRepository.count() == 0 && employeeRepository.count() == 0;
    }

    private void seedOffices() {
        if (officeRepository.count() > 0) {
            log.info("Offices đã tồn tại, bỏ qua seed offices.");
            return;
        }

        List<Office> offices = Arrays.asList(
                createOffice("Văn phòng Hà Nội", "Tầng 10, Tòa nhà Keangnam, Phạm Hùng, Nam Từ Liêm, Hà Nội"),
                createOffice("Văn phòng TP.HCM", "Tầng 15, Tòa nhà Bitexco, Quận 1, TP.HCM"),
                createOffice("Văn phòng Đà Nẵng", "Tầng 5, Tòa nhà FPT, Quận Ngũ Hành Sơn, Đà Nẵng"),
                createOffice("Văn phòng Tokyo", "Shibuya Sky Building, Tokyo, Japan"),
                createOffice("Văn phòng Osaka", "Umeda Business Center, Osaka, Japan")
        );

        officeRepository.saveAll(offices);
        log.info("Đã seed {} văn phòng", offices.size());
    }

    private Office createOffice(String name, String address) {
        Office office = new Office();
        office.setName(name);
        office.setAddress(address);
        return office;
    }

    private void seedEmployees() {
        if (employeeRepository.count() > 0) {
            log.info("Employees đã tồn tại, bỏ qua seed employees.");
            return;
        }

        List<Office> offices = officeRepository.findAll();
        if (offices.isEmpty()) {
            log.warn("Không có office nào để seed employees");
            return;
        }

        List<Employee> employees = Arrays.asList(
                // Admin users
                createEmployee("Nguyễn Văn Admin", "admin", "admin@urban.vn",
                        "123456", "0901234567", Gender.MALE,
                        "123 Đường ABC, Hà Nội", Position.MANAGER,
                        offices.get(0), Role.ADMIN),

                createEmployee("Trần Thị Quản lý", "manager", "manager@urban.vn",
                        "123456", "0901234568", Gender.FEMALE,
                        "456 Đường XYZ, TP.HCM", Position.MANAGER,
                        offices.get(1), Role.MANAGER),

                // Hà Nội Office
                createEmployee("Lê Văn Hùng", "hung.le", "hung.le@urban.vn",
                        "123456", "0901234569", Gender.MALE,
                        "789 Đường DEF, Hà Nội", Position.LEADER,
                        offices.get(0), Role.USER),

                createEmployee("Phạm Thị Lan", "lan.pham", "lan.pham@urban.vn",
                        "123456", "0901234570", Gender.FEMALE,
                        "101 Đường GHI, Hà Nội", Position.STAFF,
                        offices.get(0), Role.USER),

                createEmployee("Hoàng Minh Tuấn", "tuan.hoang", "tuan.hoang@urban.vn",
                        "123456", "0901234571", Gender.MALE,
                        "202 Đường JKL, Hà Nội", Position.STAFF,
                        offices.get(0), Role.USER),

                // TP.HCM Office
                createEmployee("Ngô Thị Mai", "mai.ngo", "mai.ngo@urban.vn",
                        "123456", "0901234572", Gender.FEMALE,
                        "303 Đường MNO, TP.HCM", Position.SUPERVISOR,
                        offices.get(1), Role.USER),

                createEmployee("Đặng Văn Nam", "nam.dang", "nam.dang@urban.vn",
                        "123456", "0901234573", Gender.MALE,
                        "404 Đường PQR, TP.HCM", Position.STAFF,
                        offices.get(1), Role.USER),

                createEmployee("Vũ Thị Hoa", "hoa.vu", "hoa.vu@urban.vn",
                        "123456", "0901234574", Gender.FEMALE,
                        "505 Đường STU, TP.HCM", Position.STAFF,
                        offices.get(1), Role.USER),

                // Đà Nẵng Office
                createEmployee("Bùi Văn Đức", "duc.bui", "duc.bui@urban.vn",
                        "123456", "0901234575", Gender.MALE,
                        "606 Đường VWX, Đà Nẵng", Position.LEADER,
                        offices.get(2), Role.USER),

                createEmployee("Lý Thị Nga", "nga.ly", "nga.ly@urban.vn",
                        "123456", "0901234576", Gender.FEMALE,
                        "707 Đường YZ, Đà Nẵng", Position.STAFF,
                        offices.get(2), Role.USER),

                // Tokyo Office
                createEmployee("Tanaka Hiroshi", "hiroshi.tanaka", "hiroshi.tanaka@urban.vn",
                        "123456", "090-1234-5577", Gender.MALE,
                        "1-1-1 Shibuya, Tokyo", Position.MANAGER,
                        offices.get(3), Role.MANAGER),

                createEmployee("Sato Yuki", "yuki.sato", "yuki.sato@urban.vn",
                        "123456", "090-1234-5578", Gender.FEMALE,
                        "2-2-2 Shinjuku, Tokyo", Position.STAFF,
                        offices.get(3), Role.USER),

                // Osaka Office
                createEmployee("Yamada Kenji", "kenji.yamada", "kenji.yamada@urban.vn",
                        "123456", "090-1234-5579", Gender.MALE,
                        "3-3-3 Umeda, Osaka", Position.SUPERVISOR,
                        offices.get(4), Role.USER),

                createEmployee("Suzuki Akiko", "akiko.suzuki", "akiko.suzuki@urban.vn",
                        "123456", "090-1234-5580", Gender.FEMALE,
                        "4-4-4 Namba, Osaka", Position.STAFF,
                        offices.get(4), Role.USER)
        );

        employeeRepository.saveAll(employees);
        log.info("Đã seed {} nhân viên", employees.size());
    }

    private Employee createEmployee(String name, String username, String email,
                                    String password, String phoneNumber, Gender gender,
                                    String address, Position position, Office office, Role role) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setUsername(username);
        employee.setEmail(email);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setPhoneNumber(phoneNumber);
        employee.setGender(gender);
        employee.setAddress(address);
        employee.setPosition(position);
        employee.setOffice(office);
        employee.setRole(role);
        return employee;
    }

    private void seedSchedules() {
        if (scheduleRepository.count() > 0) {
            log.info("Schedules đã tồn tại, bỏ qua seed schedules.");
            return;
        }

        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            log.warn("Không có employee nào để seed schedules");
            return;
        }

        // Tạo lịch làm việc cho tuần hiện tại và tuần tới
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        for (int week = 0; week < 2; week++) {
            for (int day = 0; day < 7; day++) {
                LocalDate workDate = startOfWeek.plusWeeks(week).plusDays(day);

                // Bỏ qua cuối tuần cho một số nhân viên
                if (day >= 5) { // Thứ 7 và Chủ nhật
                    continue;
                }

                for (Employee employee : employees) {
                    // Tạo lịch làm việc ngẫu nhiên cho mỗi nhân viên
                    if (Math.random() > 0.2) { // 80% khả năng có lịch làm việc
                        Schedule schedule = createSchedule(employee, workDate, getRandomWorkType());
                        scheduleRepository.save(schedule);
                    }
                }
            }
        }

        log.info("Đã seed {} lịch làm việc", scheduleRepository.count());
    }

    private Schedule createSchedule(Employee employee, LocalDate workDate, WorkType workType) {
        Schedule schedule = new Schedule();
        schedule.setEmployee(employee);
        schedule.setWorkDate(workDate);
        schedule.setWorkType(workType);

        switch (workType) {
            case NORMAL:
                schedule.setStartTime(LocalTime.of(8, 0));
                schedule.setEndTime(LocalTime.of(17, 0));
                schedule.setNotes("Làm việc bình thường");
                break;
            case OVERTIME:
                schedule.setStartTime(LocalTime.of(8, 0));
                schedule.setEndTime(LocalTime.of(20, 0));
                schedule.setNotes("Làm thêm giờ để hoàn thành dự án");
                break;
            case BUSINESS_TRIP:
                schedule.setStartTime(LocalTime.of(9, 0));
                schedule.setEndTime(LocalTime.of(18, 0));
                schedule.setNotes("Công tác tại khách hàng");
                break;
            case OUTSIDE:
                schedule.setStartTime(LocalTime.of(10, 0));
                schedule.setEndTime(LocalTime.of(16, 0));
                schedule.setNotes("Họp với đối tác bên ngoài");
                break;
            case VACATION:
                schedule.setStartTime(null);
                schedule.setEndTime(null);
                schedule.setNotes("Nghỉ phép cá nhân");
                break;
        }

        return schedule;
    }

    private WorkType getRandomWorkType() {
        WorkType[] workTypes = WorkType.values();
        double random = Math.random();

        if (random < 0.6) return WorkType.NORMAL;
        if (random < 0.75) return WorkType.OVERTIME;
        if (random < 0.85) return WorkType.BUSINESS_TRIP;
        if (random < 0.95) return WorkType.OUTSIDE;
        return WorkType.VACATION;
    }
}

