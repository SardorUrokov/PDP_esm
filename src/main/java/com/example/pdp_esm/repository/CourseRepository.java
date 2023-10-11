package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.enums.CourseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByNameAndCourseType(String name, CourseType courseType);

    Optional<Course> findByNameAndCourseType(String name, CourseType courseType);

    boolean existsByIdAndActiveTrue(Long id);

    Optional<Course> findByIdAndActiveTrue(Long id);

    Optional<Course> findByIdAndActiveFalse(Long id);

    List<Course> findAllByActiveTrue();

    List<Course> findAllByActiveFalse();

    Page<Course> findAllByPriceAndCourseType(double price, CourseType courseType, Pageable pageable);
}