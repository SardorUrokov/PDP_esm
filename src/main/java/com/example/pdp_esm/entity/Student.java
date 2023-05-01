package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName, phone;
    private double balance = 0.0;
    private char gender;

    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    @ManyToOne
    private Group group;

    public Student(String fullName, String phone, Double balance, char gender, Status status, Group group) {
        this.fullName = fullName;
        this.phone = phone;
        this.balance = balance;
        this.gender = gender;
        this.status = status;
        this.group = group;
    }
}