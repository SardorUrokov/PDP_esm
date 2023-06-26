package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResTeacherDTO;
import com.example.pdp_esm.dto.TeacherDTO;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Position;
import com.example.pdp_esm.entity.Teacher;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.PositionRepository;
import com.example.pdp_esm.repository.TeacherRepository;
import com.example.pdp_esm.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.pdp_esm.entity.enums.Roles.*;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final PositionRepository positionRepository;
    private final ReserveUsersService reserveUsersService;

    @Override
    public ApiResponse<?> createTeacher(TeacherDTO teacherDTO) {

        boolean exists = teacherRepository
                .existsByFullNameAndPhoneNumber(teacherDTO.getFullName(), teacherDTO.getPhoneNumber());

//        final boolean existedByEmail = userRepository.existsByEmail(teacherDTO.getEmail());

        if (exists) {
            return ApiResponse.builder()
                    .message("Such a Teacher is already created!")
                    .success(false)
                    .build();

//        } else if (existedByEmail) {
//            return ApiResponse.builder()
//                    .message("User with this email is already created!")
//                    .data(teacherDTO.getEmail())
//                    .success(false)
//                    .build();

        } else {
            Teacher teacher = new Teacher();
            final var teacherApiResponse = settingValues(teacher, teacherDTO);
            String otp = reserveUsersService.returnOTP(teacherApiResponse.getData());

            return ApiResponse.builder()
                    .message("Teacher Created! OTP: " + otp)
                    .success(true)
                    .data(toResTeacherDTO(Collections.singletonList(teacherApiResponse.getData())))
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return ApiResponse.builder()
                .message("All Teachers List")
                .success(true)
                .data(toResTeacherDTO(teachers))
                .build();
    }

    public ApiResponse<?> getOneTeacher(Long teacher_id) {
        Optional<Teacher> optionalTeacher = Optional.ofNullable(teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacher_id)));
        Teacher teacher = optionalTeacher.get();

        return ApiResponse.builder()
                .message("Teacher with " + teacher_id + " id")
                .success(true)
                .data(toResTeacherDTO(Collections.singletonList(teacher)))
                .build();
    }

    @Override
    public ApiResponse<?> updateTeacher(Long teacher_id, TeacherDTO teacherDTO) {

        Optional<Teacher> optionalTeacher = Optional.ofNullable(teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacher_id)));
        Teacher teacher = optionalTeacher.get();
        final var teacherApiResponse = settingValues(teacher, teacherDTO);

        return ApiResponse.builder()
                .message("Teacher Updated!")
                .success(true)
                .data(toResTeacherDTO(Collections.singletonList(teacherApiResponse.getData())))
                .build();
    }

    @Override
    public ApiResponse<?> deleteTeacher(Long teacher_id) {

        boolean exists = teacherRepository.existsByIdAndActiveTrue(teacher_id);

        if (!exists) return ApiResponse.builder()
                .message("Teacher not found!")
                .success(false)
                .build();
        else {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacher_id);
            Teacher teacher = optionalTeacher.get();
            teacher.setActive(false);
            teacherRepository.save(teacher);

            return ApiResponse.builder()
                    .message("Teacher Removed!")
                    .success(true)
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllActiveFalseTeachers() {
        List<Teacher> teacherList = teacherRepository.findAllByActiveFalse();

        if (teacherList.isEmpty()) return ApiResponse.builder()
                .message("Removed Teachers not found")
                .success(true)
                .build();

        else return ApiResponse.builder()
                .message("Removed Teachers List")
                .success(true)
                .data(toResTeacherDTO(teacherList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneActiveFalseTeacher(Long teacher_id) {

        Optional<Teacher> optionalTeacher = Optional.ofNullable(teacherRepository.findByIdAndActiveFalse(teacher_id)
                .orElseThrow(() -> new ResourceNotFoundException("Removed Teacher", "id", teacher_id)));
        Teacher teacher = optionalTeacher.get();

        return ApiResponse.builder()
                .message("Removed Teacher with " + teacher_id + " id")
                .success(true)
                .data(toResTeacherDTO(Collections.singletonList(teacher)))
                .build();
    }

    public ApiResponse<Teacher> settingValues(Teacher teacher, TeacherDTO teacherDTO) {

        Optional<Position> optionalPosition = Optional.ofNullable(positionRepository.findById(teacherDTO.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Position", "id", teacherDTO.getPositionId())));
        Position position = optionalPosition.get();

        List<Long> coursesIds = teacherDTO.getCoursesIds();
        List<Course> courses = coursesIds.stream().map(courseRepository::getById).collect(Collectors.toList());

        teacher.setFullName(teacherDTO.getFullName());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setPosition(position);
        teacher.setCourse(courses);
        teacher.setGender(teacherDTO.getGender());
        teacher.setRoles(USER);
        teacher.setActive(true);

        return new ApiResponse<>("Saved",  true, teacherRepository.save(teacher));
    }

    public List<ResTeacherDTO> toResTeacherDTO(List<Teacher> teachers) {
        return teachers.stream()
                .map(teacher -> ResTeacherDTO.builder()
                        .id(teacher.getId())
                        .fullName(teacher.getFullName())
                        .phoneNumber(teacher.getPhoneNumber())
                        .email(teacher.getEmail())
                        .position(String.valueOf(teacher.getPosition().getName()))
                        .build())
                .toList();
    }
}