package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByGroupId(Long group_id);

    boolean existsByPhoneNumberAndGroup(String phoneNumber, Group group);

    boolean existsByIdAndActiveTrue(Long id);

    List<Student> findAllByActiveFalse();

    Optional<Student> findByIdAndActiveFalse(Long id);
}