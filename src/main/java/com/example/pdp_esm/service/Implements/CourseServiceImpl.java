package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.CourseDTO;
import com.example.pdp_esm.dto.ResCourseDTOWithGroups;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.enums.CourseType;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

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
            course.setName(courseDTO.getCourseName());
            course.setPrice(courseDTO.getPrice());
            course.setActive(true);
            course.setCourseType(CourseType.valueOf(courseDTO.getCourseType()));
            Course save = courseRepository.save(course);

            //mapToDTO
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

        course.setName(courseDTO.getCourseName());
        course.setPrice(courseDTO.getPrice());
        course.setActive(courseDTO.isActive());
        course.setCourseType(CourseType.valueOf(courseDTO.getCourseType()));
        Course save = courseRepository.save(course);

        return ApiResponse.builder()
                .message("Course Updated!")
                .success(true)
                .data(toDTO(save))
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
                .message("Deleted Courses List")
                .success(true)
                .data(toDTOList(courseList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneActiveFalseCourse(Long course_id) {

        Optional<Course> courseById = Optional.ofNullable(courseRepository.findByIdAndActiveFalse(course_id)
                .orElseThrow(() -> new ResourceNotFoundException("Deleted Course", "id", course_id)));

        Course course = courseById.get();
        String message = "Deleted Course with " + course_id + " id";

        return ApiResponse.builder()
                .message(message)
                .success(true)
                .data(toDTO(course))
                .build();
    }

    //Course -> ResCourseDTOWithGroups
    public ResCourseDTOWithGroups toDTO(Course course) {

        List<Group> allByCourseName = groupRepository.findAllByCourseName(course.getName());

        return ResCourseDTOWithGroups.builder()
                .courseName(course.getName())
                .price(course.getPrice())
                .active(course.isActive())
                .courseType(String.valueOf(course.getCourseType()))
                .groupList(allByCourseName)
                .build();
    }

    //List<Course> -> List<ResCourseDTOWithGroups>
    public List<ResCourseDTOWithGroups> toDTOList(List<Course> courses) {
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }
}