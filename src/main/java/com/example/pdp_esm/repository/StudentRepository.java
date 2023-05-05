package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByGroupId(Long group_id);

    boolean existsByPhoneNumberAndGroup(String phoneNumber, Group group);

    boolean existsByIdAndActiveTrue(Long id);

    List<Student> findAllByActiveFalse();

    Optional<Student> findByIdAndActiveFalse(Long id);

    List<Student> findAllByGroupStartDateBeforeAndStatusNotAndStatusNot(LocalDate group_startDate, Status status, Status status2);

    List<Student> findAllByStatus(Status status);

}