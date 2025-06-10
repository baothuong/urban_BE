package com.urban.EmployeeManager.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data // Lombok: tự động tạo getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: tự động tạo constructor không tham số
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Lob // Cho mô tả có thể dài
    private String description;

    private String location;
    private String eventType; // Ví dụ: "Họp", "Công việc", "Nghỉ"

}
