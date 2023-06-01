package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.GroupModule;
import com.example.pdp_esm.entity.Modules;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.EduModuleRepository;
import com.example.pdp_esm.repository.ModulesRepository;
import com.example.pdp_esm.service.ModulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModulesService {

    private final ModulesRepository modulesRepository;
    private final EduModuleRepository eduModuleRepository;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponse<?> createModule(ModuleDTO moduleDTO) {

        final var exists = modulesRepository
                .existsByCourseIdAndOrdinalNumber(moduleDTO.getCourseId(), moduleDTO.getOrdinalNumber());

        if (!exists) {

            Modules module = new Modules();
            Modules modules = settingValues(module, moduleDTO);
            modules.setCreatedAt(new Date());
            final var save = modulesRepository.save(modules);

            return ApiResponse.builder()
                    .message("Module Created! ")
                    .success(true)
                    .data(save)
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
                .data(allModules)
                .build();
    }

    @Override
    public ApiResponse<?> getByModuleId(Long id) {
        final var module = modulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        return ApiResponse.builder()
                .message("Module by " + id + " id")
                .success(true)
                .data(module)
                .build();
    }

    @Override
    public ApiResponse<?> getByOrdinalNumber(Long ordinalNumber) {
        final var module = modulesRepository.findByOrdinalNumber(ordinalNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "OrdinalNumber", ordinalNumber));
        return ApiResponse.builder()
                .message("Module by " + ordinalNumber + " ordinalNumber")
                .success(true)
                .data(module)
                .build();
    }

    @Override
    public ApiResponse<?> getByCourseId(Long id) {
        final var module = modulesRepository.findByCourse_Id(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "courseId", id));
        return ApiResponse.builder()
                .message("Module by " + id + " courseId")
                .success(true)
                .data(module)
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
                .data(update)
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

        final var courseId = moduleDTO.getCourseId();
        final var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "CourseId", courseId));

        List<GroupModule> groupModules =
                moduleDTO.getEduModulesIds().stream().map(eduModuleRepository::getById).collect(Collectors.toList());

        module.setCourse(course);
        module.setGroupModules(groupModules);
        module.setOrdinalNumber(moduleDTO.getOrdinalNumber());
        return module;

//        return Modules.builder()
//                .course(course)
//                .groupModules(groupModules)
//                .ordinalNumber(moduleDTO.getOrdinalNumber())
//                .build();
    }
}