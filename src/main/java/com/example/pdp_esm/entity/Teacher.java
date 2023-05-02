package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import com.example.pdp_esm.entity.enums.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class Teacher extends User{

    @ManyToOne
    private Position position;

    @ManyToMany
    private List<Course> course;

    public Teacher(String fullName, String phoneNumber, String email, String password, char gender, Roles roles, boolean active, Position position, List<Course> course) {
        super(fullName, phoneNumber, email, password, gender, roles, active);
        this.position = position;
        this.course = course;
    }
}