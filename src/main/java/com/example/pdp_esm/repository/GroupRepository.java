package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByGroupNameAndCourseNameAndActiveTrue(String groupName, String course_name);

    boolean existsByIdAndActiveTrue(Long id);

    List<Group> findAllByActiveFalse();

    Optional<Group> findByIdAndActiveFalse(Long id);

    List<Group> findAllByCourseName(String course_name);

    List<Group> findAllByCourse_NameContainingIgnoreCaseAndGroupNameContainingIgnoreCaseOrderByCourse_Name(String course_name, String groupName);

}
