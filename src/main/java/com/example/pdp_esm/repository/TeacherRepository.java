package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByFullNameAndPhoneNumber(String fullName, String phoneNumber);

    boolean existsByIdAndActiveTrue(Long id);

    List<Teacher> findAllByActiveFalse();

    Optional<Teacher> findByIdAndActiveFalse(Long id);
}