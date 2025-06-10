package com.urban.EmployeeManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fullName;



    private String username;




    private String email;


    private String phone;
    private String gender;

    private String address;
    private String position;

    private String office;

    private String avatar;

    private LocalDateTime createdAt;



    private LocalDateTime updatedAt;


    private Boolean isActive = true;

}

