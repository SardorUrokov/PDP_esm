package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByCourseName(String course_name);
}
