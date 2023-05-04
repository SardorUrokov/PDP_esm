package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "select * from Teacher where Teacher.id = :id", nativeQuery = true)
    Teacher getTeacherById(Long id);
}
