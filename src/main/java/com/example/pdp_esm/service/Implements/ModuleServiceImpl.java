package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.GroupModuleDTO;
import com.example.pdp_esm.dto.ModuleDTO;
import com.example.pdp_esm.dto.result.ResGroupModule;
import com.example.pdp_esm.dto.result.ResModule;
import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.repository.*;
import com.example.pdp_esm.service.ModulesService;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModulesService {

    private final ModulesRepository modulesRepository;
    private final GroupModuleService groupModuleService;
    private final AbstractModuleRepository abstractModuleRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse<?> createModule(ModuleDTO moduleDTO) {

        final var absModuleId = moduleDTO.getAbsModuleId();
        final var groupId = moduleDTO.getGroupId();
        final var ordinalNumber = moduleDTO.getOrdinalNumber();

        final var optionalModules = modulesRepository
                .findByCourseIdAndOrdinalNumberAndGroupId(absModuleId, ordinalNumber, groupId);

        final var abstractModule = abstractModuleRepository.findById(absModuleId)
                .orElseThrow(() -> new ResourceNotFoundException("Abstract Module", "absModuleId", absModuleId));

        final var optionalGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "group_id", groupId));

        final var courseIdByGroup = optionalGroup.getCourse().getId();
        final var courseId = abstractModule.getCourse().getId();

        if (ordinalNumber > abstractModule.getModules())
            return
                    new ApiResponse<>("Ordinal Number of Modul can't be bigger than moduls count", false);

        else if (!courseId.equals(courseIdByGroup))
            return
                    new ApiResponse<>("Module's Course and Groups Course doesn't match!", false);

        else if (optionalModules.isEmpty()) {
            Modules module = new Modules();
            Modules modules = settingValues(module, moduleDTO);
            modules.setCreatedAt(new Date());
            Modules save = modulesRepository.save(modules);

            return ApiResponse.builder()
                    .message("Module Created! ")
                    .success(true)
                    .data(toResModule(save))
                    .build();
        } else
            return new ApiResponse<>("Such a Module has already created!", false);
    }

    @Override
    public ApiResponse<?> getAllModule() {
        List<Modules> allModules = modulesRepository.findAll();

        return ApiResponse.builder()
                .message("All Modules List")
                .success(true)
                .data(toResModuleList(allModules))
                .build();
    }

    @Override
    public ApiResponse<?> getByModuleId(Long id) {
        final var module = modulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));

        return ApiResponse.builder()
                .message("Module by " + id + " id")
                .success(true)
                .data(toResModule(module))
                .build();
    }

    @Override
    public ApiResponse<?> getByOrdinalNumber(Long ordinalNumber) {
        final var module = modulesRepository.findByOrdinalNumber(ordinalNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "OrdinalNumber", ordinalNumber));

        return ApiResponse.builder()
                .message("Module by " + ordinalNumber + " ordinalNumber")
                .success(true)
                .data(toResModule(module))
                .build();
    }

    @Override
    public ApiResponse<?> getByCourseId(Long id) {
        final var module = modulesRepository.findByAbstractModule_Course_Id(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "courseId", id));

        return ApiResponse.builder()
                .message("Module by " + id + " courseId")
                .success(true)
                .data(toResModule(module))
                .build();
    }

    @Override
    public ApiResponse<?> updateModule(Long id, ModuleDTO moduleDTO) {
        Modules module = modulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));

        Modules updatedModule = settingValues(module, moduleDTO);
        updatedModule.setUpdatedAt(new Date());
        Modules update = modulesRepository.save(updatedModule);

        return ApiResponse.builder()
                .message("Module Updated!")
                .success(true)
                .data(toResModule(update))
                .build();
    }

    @Override
    public ApiResponse<?> deleteModule(Long id) {

        if (modulesRepository.existsById(id)) {
            modulesRepository.deleteById(id);
            return new ApiResponse<>("Module Deleted!", true);
        } else
            return new ApiResponse<>("Module not found!", false);
    }

    public Modules settingValues(Modules module, ModuleDTO moduleDTO) {
        final var abstractModule = abstractModuleRepository.findById(moduleDTO.getAbsModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Course Module", "id", moduleDTO.getAbsModuleId()));

        List<GroupModule> groupModulesList = new ArrayList<>();
        GroupModule groupModule = groupModuleService.createModule(
                GroupModuleDTO.builder()
                        .groupId(moduleDTO.getGroupId())
                        .build());
        groupModulesList.add(groupModule);

        final var moduleName = createModuleName(
                abstractModule.getCourse().getName(),
                moduleDTO.getOrdinalNumber(),
                groupModulesList);

        module.setAbstractModule(abstractModule);
        module.setName(moduleName);
        module.setGroupModules(groupModulesList);
        module.setOrdinalNumber(moduleDTO.getOrdinalNumber());
        return module;
    }

    private static String createModuleName(String courseName, Long ordinalNumber, List<GroupModule> groupModules) {

            StringBuilder examNameBuilder = new StringBuilder();
            for (GroupModule groupModule : groupModules) {
                examNameBuilder.append(groupModule.getGroup().getGroupName()).append("_");
            }
            examNameBuilder.setLength(examNameBuilder.length() - 1);

            String groupNames =  examNameBuilder.toString();

        return courseName + " " + ordinalNumber + "-modul_" + groupNames;
    }

    public ResModule toResModule(Modules module) {
        List<ResGroupModule> resModuleDTOList =
                groupModuleService.toResModuleDTOList(module.getGroupModules());

        return ResModule.builder()
                .ordinalNumber(module.getOrdinalNumber())
                .name(module.getName())
                .courseName(module.getAbstractModule().getCourse().getName())
                .groupModuleList(resModuleDTOList)
                .build();
    }

    public List<ResModule> toResModuleList(List<Modules> modules) {
        return modules.stream().map(this::toResModule).toList();
    }
}