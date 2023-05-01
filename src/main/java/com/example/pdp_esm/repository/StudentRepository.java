package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
