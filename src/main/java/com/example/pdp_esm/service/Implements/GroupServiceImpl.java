package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.*;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResGroupDTO;
import com.example.pdp_esm.dto.result.ResStudentDTO;
import com.example.pdp_esm.dto.result.ResTeacherDTO;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.Teacher;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.repository.TeacherRepository;
import com.example.pdp_esm.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentServiceImpl studentService;
    private final TeacherRepository teacherRepository;
    private final TeacherServiceImpl teacherService;

    @Override
    public ApiResponse<?> createGroup(GroupDTO groupDTO) {
        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findById(groupDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", groupDTO.getCourseId())));
        String courseName = optionalCourse.get().getName();

        List<Long> teacherIds = groupDTO.getTeacherIds();
        List<Teacher> teachers =
                teacherIds.stream().map(teacherRepository::getById).toList();

        boolean empty = teachers.isEmpty();
        boolean existGroup = groupRepository.existsByGroupNameAndCourseNameAndActiveTrue(groupDTO.getGroupName(), courseName);
        LocalDate date = LocalDate.parse(groupDTO.getStartsDate());

        if (existGroup) {
            return ApiResponse.builder()
                    .message("Such a group is already saved!")
                    .success(false)
                    .build();

        } else if (empty) return ApiResponse.builder()
                .message("Teachers not found with this " + groupDTO.getTeacherIds().toString() + " id")
                .success(false)
                .build();

        else if (date.isBefore(LocalDate.now())) return ApiResponse.builder()
                .message("Groups Starts Date can't be Past")
                .success(false)
                .build();
        else {
            Group group = new Group();
            Group save = settingValues(group, groupDTO);
            return ApiResponse.builder()
                    .message("Group Created")
                    .success(true)
                    .data(toResDTO(save))
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllGroups(String courseName, String search) {
        List<Group> groupList = groupRepository
                .findAllByCourse_NameContainingIgnoreCaseAndGroupNameContainingIgnoreCaseOrderByCourse_Name(courseName, search);

        return ApiResponse.builder()
                .message("Groups List! ")
                .success(true)
                .data(toDTOList(groupList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneGroup(Long group_id) {
        Optional<Group> optionalGroup = Optional.ofNullable(groupRepository.findById(group_id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", group_id)));
        Group group = optionalGroup.get();

        return ApiResponse.builder()
                .message("Group with " + group_id + " id")
                .success(true)
                .data(toResDTO(group))
                .build();
    }

    @Override
    public ApiResponse<?> updateGroup(Long group_id, GroupDTO groupDTO) {
        Optional<Group> optionalGroup = Optional.ofNullable(groupRepository.findById(group_id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", group_id)));
        Group group = optionalGroup.get();
        Group save = settingValues(group, groupDTO);

        return ApiResponse.builder()
                .message("Group Updated!")
                .success(true)
                .data(toResDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> deleteGroup(Long group_id) {

        boolean exists = groupRepository.existsByIdAndActiveTrue(group_id);
        if (!exists) return ApiResponse.builder()
                .message("Group not found!")
                .success(false)
                .build();
        else {
            Optional<Group> optionalGroup = groupRepository.findById(group_id);
            Group group = optionalGroup.get();
            group.setActive(false);
            groupRepository.save(group);
            return ApiResponse.builder()
                    .message("Group Terminated!")
                    .success(true)
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllActiveFalseGroups() {
        List<Group> groupList = groupRepository.findAllByActiveFalse();

        return ApiResponse.builder()
                .message("Terminated Groups List")
                .success(true)
                .data(toDTOList(groupList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneActiveFalseGroup(Long group_id) {
        Optional<Group> optionalGroup = Optional.ofNullable(groupRepository.findByIdAndActiveFalse(group_id)
                .orElseThrow(() -> new ResourceNotFoundException("Terminated Group", "id", group_id)));
        Group group = optionalGroup.get();

        return ApiResponse.builder()
                .message("Terminated Group with " + group_id + " id")
                .success(true)
                .data(toResDTO(group))
                .build();
    }

    public Group settingValues(Group group, GroupDTO groupDTO) {
        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findById(groupDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", groupDTO.getCourseId())));
        Course course = optionalCourse.get();

        List<Long> teacherIds = groupDTO.getTeacherIds();
        List<Teacher> teachers = teacherIds.stream().map(teacherRepository::getById).toList();

        group.setGroupName(groupDTO.getGroupName());
        group.setCourse(course);
        group.setTeacher(teachers);
        group.setStartDate(LocalDate.parse(groupDTO.getStartsDate()));
        group.setActive(true);
        return groupRepository.save(group);
    }

    public ResGroupDTO toResDTO(Group group) {

        List<Student> students = studentRepository.findAllByGroupId(group.getId());
        List<ResStudentDTO> resStudentDTOList = studentService.toResStudentDTO(students);

        List<Teacher> groupTeachers = group.getTeacher();
        List<ResTeacherDTO> resTeacherDTOList = teacherService.toResTeacherDTO(groupTeachers);

        return ResGroupDTO.builder()
                .groupName(group.getGroupName())
                .courseName(group.getCourse().getName())
                .courseType(group.getCourse().getCourseType().toString())
                .active(group.isActive())
                .startsDate(String.valueOf(group.getStartDate()))
                .teachers(resTeacherDTOList)
                .students(resStudentDTOList)
                .build();
    }

    public List<ResGroupDTO> toDTOList(List<Group> groups) {
        return groups.stream().map(this::toResDTO).toList();
    }
}