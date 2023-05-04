package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.Roles;
import com.example.pdp_esm.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class Student extends User{

    private double balance = 0.0;

    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    @ManyToOne
    private Group group;

    public Student(String fullName, String phoneNumber, String email, String password, String gender, Roles roles, boolean active, double balance, Status status, Group group) {
        super(fullName, phoneNumber, email, password, gender, roles, active);
        this.balance = balance;
        this.status = status;
        this.group = group;
    }
}