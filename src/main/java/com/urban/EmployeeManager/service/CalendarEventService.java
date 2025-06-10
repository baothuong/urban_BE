package com.urban.EmployeeManager.service;

import com.urban.EmployeeManager.model.CalendarEvent;
import com.urban.EmployeeManager.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    public List<CalendarEvent> getEvents(LocalDateTime start, LocalDateTime end) {
        return calendarEventRepository.findAllByStartTimeBetween(start, end);
    }

    public Optional<CalendarEvent> getEventById(Long id) {
        return calendarEventRepository.findById(id);
    }

    public CalendarEvent createEvent(CalendarEvent event) {
        // Thêm validation nếu cần
        return calendarEventRepository.save(event);
    }

    public Optional<CalendarEvent> updateEvent(Long id, CalendarEvent eventDetails) {
        return calendarEventRepository.findById(id).map(event -> {
            event.setTitle(eventDetails.getTitle());
            event.setStartTime(eventDetails.getStartTime());
            event.setEndTime(eventDetails.getEndTime());
            event.setDescription(eventDetails.getDescription());
            event.setLocation(eventDetails.getLocation());
            event.setEventType(eventDetails.getEventType());
            return calendarEventRepository.save(event);
        });
    }

    public boolean deleteEvent(Long id) {
        if (calendarEventRepository.existsById(id)) {
            calendarEventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

