package com.example.pdp_esm.entity;

import lombok.*;
import java.util.List;
import jakarta.persistence.*;
import com.example.pdp_esm.entity.enums.Roles;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends User{

    @ManyToOne
    private Position position;

    @ManyToMany
    private List<Course> course;

    private String email, password;

    public Teacher(String fullName, String phoneNumber, String email, String password, String gender, Roles roles, boolean active, Position position, List<Course> course) {
        super(fullName, phoneNumber, email, password, gender, roles, active);
        this.position = position;
        this.course = course;
    }
}