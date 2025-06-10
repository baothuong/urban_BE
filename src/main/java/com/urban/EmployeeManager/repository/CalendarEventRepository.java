package com.urban.EmployeeManager.repository;

import com.urban.EmployeeManager.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urban.EmployeeManager.model.CalendarEvent;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findAllByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}

