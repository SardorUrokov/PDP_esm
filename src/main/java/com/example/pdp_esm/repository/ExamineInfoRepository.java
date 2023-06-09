package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.ExamineInfo;
import com.example.pdp_esm.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExamineInfoRepository extends JpaRepository<ExamineInfo, Long> {

    boolean existsByStartsDateAndCoursesAndGroups(Date startsDate, List<Course> courses, List<Group> groups);

    Optional<ExamineInfo> findByStartsDate(Date startsDate);

}