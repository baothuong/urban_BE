// ScheduleUpdateDTO.java
package com.urban.EmployeeManager.dto;

import com.urban.EmployeeManager.enums.WorkType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleUpdateDTO {
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private WorkType workType;
    private String notes;
}

