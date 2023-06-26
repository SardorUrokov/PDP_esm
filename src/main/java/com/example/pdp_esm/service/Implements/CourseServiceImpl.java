package com.example.pdp_esm.service.Implements;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.dto.CourseDTO;
import com.example.pdp_esm.service.CourseService;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResGroupDTO;
import com.example.pdp_esm.entity.enums.CourseType;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.dto.result.ResCourseDTOWithGroups;
import com.example.pdp_esm.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final GroupServiceImpl groupService;

    @Override
    public ApiResponse<?> createCourse(CourseDTO courseDTO) {

        boolean existsByNameAndCourseType = courseRepository
                .existsByNameAndCourseType(courseDTO.getCourseName(), CourseType.valueOf(courseDTO.getCourseType()));

        if (existsByNameAndCourseType) {
            return ApiResponse.builder()
                    .message("Such a course is already saved!")
                    .success(false)
                    .build();
        } else {
            Course course = new Course();
            Course save = settingValues(course, courseDTO);
            return ApiResponse.builder()
                    .message("Saved")
                    .success(true)
                    .data(toDTO(save))
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllCourses() {
        List<Course> courseList = courseRepository.findAllByActiveTrue();

        return ApiResponse.builder()
                .message("Courses List")
                .success(true)
                .data(toDTOList(courseList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneCourse(Long course_id) {
        Optional<Course> courseById = Optional.ofNullable(courseRepository.findByIdAndActiveTrue(course_id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", course_id)));

        Course course = courseById.get();
        String message = "Course with " + course_id + " id";

        return ApiResponse.builder()
                .message(message)
                .success(true)
                .data(toDTO(course))
                .build();
    }

    @Override
    public ApiResponse<?> updateCourse(Long course_id, CourseDTO courseDTO) {

        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findByIdAndActiveTrue(course_id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", course_id)));
        Course course = optionalCourse.get();
        Course updated = settingValues(course, courseDTO);

        return ApiResponse.builder()
                .message("Course Updated!")
                .success(true)
                .data(toDTO(updated))
                .build();
    }

    @Override
    public ApiResponse<?> deleteCourse(Long course_id) {

        boolean exists = courseRepository.existsByIdAndActiveTrue(course_id);

        if (!exists) return ApiResponse.builder()
                .message("Course not found!")
                .success(false)
                .build();
        else {
            Optional<Course> optionalCourse = courseRepository.findById(course_id);
            Course course = optionalCourse.get();
            course.setActive(false);
            courseRepository.save(course);

            return ApiResponse.builder()
                    .message("Course Terminated!")
                    .success(true)
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllActiveFalseCourses() {
        List<Course> courseList = courseRepository.findAllByActiveFalse();

        return ApiResponse.builder()
                .message("Terminated Courses List")
                .success(true)
                .data(toDTOList(courseList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneActiveFalseCourse(Long course_id) {

        Optional<Course> courseById = Optional.ofNullable(courseRepository.findByIdAndActiveFalse(course_id)
                .orElseThrow(() -> new ResourceNotFoundException("Deleted Course", "id", course_id)));
        Course course = courseById.get();

        return ApiResponse.builder()
                .message("Terminated Course with " + course_id + " id")
                .success(true)
                .data(toDTO(course))
                .build();
    }

    public ResCourseDTOWithGroups toDTO(Course course) {

        List<Group> allByCourseName = groupRepository.findAllByCourseName(course.getName());
        List<ResGroupDTO> resGroupDTOList = groupService.toDTOList(allByCourseName);

        return ResCourseDTOWithGroups.builder()
                .course_id(course.getId())
                .courseName(course.getName())
                .price(course.getPrice())
                .active(course.isActive())
                .courseType(String.valueOf(course.getCourseType()))
                .groupList(resGroupDTOList)
                .build();
    }

    public Course settingValues(Course course, CourseDTO courseDTO) {
        course.setName(courseDTO.getCourseName());
        course.setPrice(courseDTO.getPrice());
        course.setActive(true);
        course.setCourseType(CourseType.valueOf(courseDTO.getCourseType()));
        return courseRepository.save(course);
    }

    public List<ResCourseDTOWithGroups> toDTOList(List<Course> courses) {
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Set<ResCourseDTOWithGroups> toDTOSet (Set<Course> courses){
        return courses.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}