package com.example.pdp_esm.repository;

import com.example.pdp_esm.dto.ResStudentDTO;
import com.example.pdp_esm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByGroupId(Long group_id);
//    List<ResStudentDTO> findAllByGroupId(Long group_id);
}
