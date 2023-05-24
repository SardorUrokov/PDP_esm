package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.EduModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResModuleDTO;
import com.example.pdp_esm.entity.EduModule;
import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.EduModuleRepository;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        EduModule module = new EduModule();
        EduModule save = settingValues(module, moduleDTO);

        return ApiResponse.builder()
                .message("Module Crated!")
                .success(true)
                .data(toResModuleDTO(save))
                .build();
    }

    public ApiResponse<?> getOne(Long id) {
        final var byId = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EduModule", "id", id));

        return ApiResponse.builder()
                .message("EduModule with " + id + " id")
                .success(true)
                .data(toResModuleDTO(byId))
                .build();
    }

    public ApiResponse<?> getAllModules() {
        final var all = moduleRepository.findAll();
        return new ApiResponse<>("All Modules List", true, toResModuleDTOList(all));
    }

    public ApiResponse<?> updateResponse(Long id, EduModuleDTO moduleDTO){
        final var byId = moduleRepository.findById(id);
        final var module = settingValues(byId.get(), moduleDTO);

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


    public ResModuleDTO toResModuleDTO(EduModule module){

        final var groups = groupService.toDTOList(module.getGroups());
        final var resExamResultDTOList = examResultService.toResExamResultDTOList(module.getExamResults());

        return ResModuleDTO.builder()
                .moduleName(module.getModuleName())
                .ordinalNumber(String.valueOf(module.getOrdinalNumber()))
                .moduleGroups(groups)
                .moduleExamResults(resExamResultDTOList)
                .build();
    }

    public List<ResModuleDTO> toResModuleDTOList(List<EduModule> eduModuleList) {
        return eduModuleList.stream().map(this::toResModuleDTO).toList();
    }

    public EduModule settingValues(EduModule module, EduModuleDTO moduleDTO){

        List<Group> groupList = moduleDTO.getGroupIds().stream().map(groupRepository::getById).toList();
        List<ExamResult> examResultList = moduleDTO.getExamResultIds().stream().map(examResultRepository::getById).toList();

        module.setOrdinalNumber(moduleDTO.getOrdinalNumber());
        module.setModuleName(moduleDTO.getOrdinalNumber() + "-modul");
        module.setGroups(groupList);
        module.setExamResults(examResultList);
        moduleRepository.save(module);

        return module;
    }
}