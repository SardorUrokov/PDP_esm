package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.GroupModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResExamResults;
import com.example.pdp_esm.dto.result.ResGroupDTO;
import com.example.pdp_esm.dto.result.ResGroupModule;
import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.GroupModule;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.GroupModuleRepository;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GroupModuleService {

    private final GroupServiceImpl groupService;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final ExamResultServiceImpl examResultService;
    private final ExamResultRepository examResultRepository;
    private final GroupModuleRepository groupModuleRepository;

    public GroupModule createModule(GroupModuleDTO moduleDTO) {

        final var groupId = moduleDTO.getGroupId();
        final var exists = groupModuleRepository.existsByGroup_Id(groupId);

        if (!exists) {
            GroupModule module = new GroupModule();
            GroupModule save = settingValues(module, moduleDTO);
            return save;
        } else return null;
    }
/*
    public ApiResponse<?> getAllModules() {
        final var all = groupModuleRepository.findAll();
        return new ApiResponse<>("All Modules List", true, toResModuleDTOList(all));
    }

    public ApiResponse<?> getOne(Long id) {
        final var byId = groupModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupModule", "id", id));

        return ApiResponse.builder()
                .message("GroupModule with " + id + " id")
                .success(true)
                .data(toResModuleDTO(byId))
                .build();
    }

    public ApiResponse<?> updateModule(Long id, GroupModuleDTO moduleDTO) {

        final var optionalModulePerGroup = groupModuleRepository.findById(id);
        final var module = settingValues(optionalModulePerGroup.get(), moduleDTO);

        return ApiResponse.builder()
                .message("Module Updated!")
                .success(true)
                .data(toResModuleDTO(module))
                .build();
    }

    public ApiResponse<?> deleteModule(Long module_id) {
        if (groupModuleRepository.existsById(module_id)) {
            groupModuleRepository.deleteById(module_id);
            return new ApiResponse<>("Module Deleted!", true);
        } else
            return new ApiResponse<>("Module not found!", false);
    }
*/
    public ResGroupModule toResModuleDTO(GroupModule module) {

        ResGroupDTO group = groupService.toResDTO(module.getGroup());
        List<ResExamResults> resExamResultDTOList =
                examResultService.toResExamResultDTOList(module.getExamResults());

        return ResGroupModule.builder()
                .createdAt(module.getCreatedAt().toString())
                .moduleGroup(group)
                .moduleExamResults(resExamResultDTOList)
                .build();
    }

    public List<ResGroupModule> toResModuleDTOList(List<GroupModule> eduModuleList) {
        return eduModuleList.stream().map(this::toResModuleDTO).toList();
    }

    public GroupModule settingValues(GroupModule module, GroupModuleDTO moduleDTO) {

        final var group = groupRepository.findById(moduleDTO.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", moduleDTO.getGroupId()));
//        List<ExamResult> examResultList = new ArrayList<>();
//        for (Student student : studentRepository.findAllByGroupId(group.getId())) {
//            final var examResult = examResultRepository.findByStudentId(student.getId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "Student id", student.getId()));
//            examResultList.add(examResult);
//        }
/*
        List<ExamResult> examResultList = studentRepository
                .findAllByGroupId(moduleDTO.getGroupId())
                .stream()
                .map(Student::getId)
                .map(studentId -> examResultRepository
                        .findByStudentId(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "Student id", studentId))
                )
                .collect(Collectors.toList());
*/
        List<ExamResult> examResultList = studentRepository
                .findAllByGroupId(moduleDTO.getGroupId())
                .stream()
                .map(Student::getId)
                .flatMap(studentId -> examResultRepository
                        .findByStudentId(studentId).stream()
                )
                .collect(Collectors.toList());
        module.setCreatedAt(new Date());
        module.setGroup(group);
        module.setExamResults(examResultList);
        groupModuleRepository.save(module);
        return module;
    }
}