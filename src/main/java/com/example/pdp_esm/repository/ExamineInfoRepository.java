package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.ExamineInfo;
import com.example.pdp_esm.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ExamineInfoRepository extends JpaRepository<ExamineInfo, Long> {

    boolean existsByStartsDateAndCoursesInAndGroupsIn(Date startsDate, Set<Course> courses, Set<Group> groups);

    Optional<ExamineInfo> findByGroupsIn(Set<Group> groups);

    Optional<ExamineInfo> findByStartsDate(Date startsDate);

}