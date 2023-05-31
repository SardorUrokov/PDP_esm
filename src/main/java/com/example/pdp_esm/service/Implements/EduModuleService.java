package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.EduModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResModuleDTO;
import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.GroupModule;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.EduModuleRepository;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EduModuleService {

    private final EduModuleRepository moduleRepository;
    private final GroupRepository groupRepository;
    private final ExamResultRepository examResultRepository;
    private final GroupServiceImpl groupService;
    private final ExamResultServiceImpl examResultService;

    public ApiResponse<?> createModule(EduModuleDTO moduleDTO) {

        GroupModule module = new GroupModule();
        GroupModule save = settingValues(module, moduleDTO);

        return ApiResponse.builder()
                .message("Module Created!")
                .success(true)
                .data(toResModuleDTO(save))
                .build();
    }

    public ApiResponse<?> getAllModules() {
        final var all = moduleRepository.findAll();
        return new ApiResponse<>("All Modules List", true, toResModuleDTOList(all));
    }

    public ApiResponse<?> getOne(Long id) {
        final var byId = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupModule", "id", id));

        return ApiResponse.builder()
                .message("GroupModule with " + id + " id")
                .success(true)
                .data(toResModuleDTO(byId))
                .build();
    }

    public ApiResponse<?> updateModule(Long id, EduModuleDTO moduleDTO){
        final var optionalModulePerGroup = moduleRepository.findById(id);
        final var module = settingValues(optionalModulePerGroup.get(), moduleDTO);

        return ApiResponse.builder()
                .message("Module Updated!")
                .success(true)
                .data(toResModuleDTO(module))
                .build();
    }

    public ApiResponse<?> deleteModule(Long module_id) {

        if (moduleRepository.existsById(module_id)) {
            moduleRepository.deleteById(module_id);
            return new ApiResponse<>("Module Deleted!", true);
        } else
            return new ApiResponse<>("Module not found!", false);
    }


    public ResModuleDTO toResModuleDTO(GroupModule module){

        final var group = groupService.toResDTO(module.getGroup());
        final var resExamResultDTOList = examResultService.toResExamResultDTOList(module.getExamResults());

        return ResModuleDTO.builder()
                .createdAt(module.getCreatedAt().toString())
                .moduleGroup(group)
                .moduleExamResults(resExamResultDTOList)
                .build();
    }

    public List<ResModuleDTO> toResModuleDTOList(List<GroupModule> eduModuleList) {
        return eduModuleList.stream().map(this::toResModuleDTO).toList();
    }

    public GroupModule settingValues(GroupModule module, EduModuleDTO moduleDTO){

//        List<Group> groupList = moduleDTO.getGroupIds().stream().map(groupRepository::getById).toList();
        List<ExamResult> examResultList = moduleDTO.getExamResultIds().stream().map(examResultRepository::getById).toList();

        final var optionalGroup = groupRepository.findById(moduleDTO.getGroupId());

        module.setCreatedAt(new Date());
        module.setGroup(optionalGroup.get());
        module.setExamResults(examResultList);
        moduleRepository.save(module);

        return module;
    }
}