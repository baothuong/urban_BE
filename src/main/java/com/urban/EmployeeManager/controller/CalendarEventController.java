package com.urban.EmployeeManager.controller;
import com.urban.EmployeeManager.model.CalendarEvent;
import com.urban.EmployeeManager.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
// @CrossOrigin cấu hình ở WebConfig tốt hơn cho quản lý tập trung
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @GetMapping
    public ResponseEntity<List<CalendarEvent>> getEvents(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(calendarEventService.getEvents(start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEvent> getEventById(@PathVariable Long id) {
        return calendarEventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CalendarEvent> createEvent(@RequestBody CalendarEvent event) {
        CalendarEvent createdEvent = calendarEventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEvent> updateEvent(@PathVariable Long id, @RequestBody CalendarEvent eventDetails) {
        return calendarEventService.updateEvent(id, eventDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (calendarEventService.deleteEvent(id)) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.notFound().build(); // HTTP 404
    }
}

