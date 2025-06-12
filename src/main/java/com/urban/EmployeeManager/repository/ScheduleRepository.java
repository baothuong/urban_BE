package com.urban.EmployeeManager.repository;

import com.urban.EmployeeManager.model.Schedule;
import com.urban.EmployeeManager.enums.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeeId(Long employeeId);

    List<Schedule> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM Schedule s WHERE s.employee.id = :employeeId " +
            "AND s.workDate BETWEEN :startDate AND :endDate")
    List<Schedule> findByEmployeeIdAndDateRange(@Param("employeeId") Long employeeId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Schedule s WHERE s.workDate BETWEEN :startDate AND :endDate " +
            "AND (:officeId IS NULL OR s.employee.office.id = :officeId) " +
            "AND (:workType IS NULL OR s.workType = :workType)")
    List<Schedule> findByFilters(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 @Param("officeId") Long officeId,
                                 @Param("workType") WorkType workType);
}

