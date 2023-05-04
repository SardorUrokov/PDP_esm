package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.ResTeacherDTO;
import com.example.pdp_esm.dto.TeacherDTO;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Position;
import com.example.pdp_esm.entity.Teacher;
import com.example.pdp_esm.entity.enums.Roles;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.PositionRepository;
import com.example.pdp_esm.repository.TeacherRepository;
import com.example.pdp_esm.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.pdp_esm.entity.enums.Roles.*;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final PositionRepository positionRepository;

    @Override
    public ApiResponse<?> create(TeacherDTO teacherDTO) {

        Optional<Position> optionalPosition = Optional.ofNullable(positionRepository.findById(teacherDTO.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Position", "id", teacherDTO.getPositionId())));
        Position position = optionalPosition.get();

        List<Long> coursesIds = teacherDTO.getCoursesIds();
        List<Course> courses = coursesIds.stream().map(courseRepository::getById).toList();

        Teacher teacher = new Teacher();
        teacher.setFullName(teacherDTO.getFullName());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setPosition(position);
        teacher.setCourse(courses);
        teacher.setGender(teacherDTO.getGender());
        teacher.setRoles(USER);
        teacher.setActive(true);
        Teacher save = teacherRepository.save(teacher);

        return ApiResponse.builder()
                .message("Teacher Created!")
                .success(true)
                .data(save)
                .build();
    }

    @Override
    public ApiResponse<?> getAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return ApiResponse.builder()
                .message("All Teachers List")
                .success(true)
                .data(toResTeacherDTO(teachers))
                .build();
    }

    public List<ResTeacherDTO> toResTeacherDTO(List<Teacher> teachers) {
        return teachers.stream()
                .map(teacher -> ResTeacherDTO.builder()
                        .fullName(teacher.getFullName())
                        .phoneNumber(teacher.getPhoneNumber())
                        .email(teacher.getEmail())
                        .position(String.valueOf(teacher.getPosition().getName()))
                        .build())
                .toList();
    }
}
