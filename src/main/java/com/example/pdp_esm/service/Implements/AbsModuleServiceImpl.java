package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.AbsModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.AbstractModule;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.AbstractModuleRepository;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.service.AbsModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AbsModuleServiceImpl implements AbsModuleService {

    private final AbstractModuleRepository abstractModuleRepository;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponse<?> createAbsModule(AbsModuleDTO absModuleDTO) {

        final var exists = abstractModuleRepository
                .existsByCourseIdAndModules(absModuleDTO.getCourseId(), absModuleDTO.getModules());

        if (!exists) {
            AbstractModule abstractModule = new AbstractModule();
            final var absModule = settingValues(abstractModule, absModuleDTO);
            absModule.setCreatedAt(new Date());
            abstractModuleRepository.save(absModule);

            return ApiResponse.builder()
                    .message("Course Module created!")
                    .success(true)
                    .data(absModule)
                    .build();
        } else
            return new ApiResponse<>("Such a Course Module is already created!", false);
    }

    @Override
    public ApiResponse<?> getAllAbsModules() {
        final var all = abstractModuleRepository.findAll();
        return ApiResponse.builder()
                .message("All Course Modules List ")
                .success(true)
                .data(all)
                .build();
    }

    @Override
    public ApiResponse<?> getOneAbsModule(Long id) {
        final var byId = abstractModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Module", "id", id));

        return ApiResponse.builder()
                .message("Course Module by " + id + " id")
                .success(true)
                .data(byId)
                .build();
    }

    @Override
    public ApiResponse<?> getByCourseId(Long id) {
        final var byCourseId = abstractModuleRepository.findByCourse_Id(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Module", "course_id", id));

        return ApiResponse.builder()
                .message("Course Module by " + id + " course_id")
                .success(true)
                .data(byCourseId)
                .build();
    }

    @Override
    public ApiResponse<?> getByModule(Long module) {
        final var byModules = abstractModuleRepository.findByModules(module)
                .orElseThrow(() -> new ResourceNotFoundException("Course Module", "module", module));

        return ApiResponse.builder()
                .message("Course Module by " + module + " modules")
                .success(true)
                .data(byModules)
                .build();
    }

    @Override
    public ApiResponse<?> updateAbsModule(Long id, AbsModuleDTO dto) {

        final var byId = abstractModuleRepository.findById(id);
        final var abstractModule = settingValues(byId.get(), dto);
        abstractModule.setUpdatedAt(new Date());
        abstractModuleRepository.save(abstractModule);

        return ApiResponse.builder()
                .message("Course Module updated!")
                .success(true)
                .data(abstractModule)
                .build();
    }

    @Override
    public ApiResponse<?> deleteAbsModule(Long id) {
        final var exists = abstractModuleRepository.existsById(id);
        if (exists) {
            abstractModuleRepository.deleteById(id);
            return new ApiResponse<>("Course Module Deleted!", true);
        } else
            return new ApiResponse<>("Course Module not found!", false);
    }

    public AbstractModule settingValues(AbstractModule abstractModule, AbsModuleDTO absModuleDTO) {

        final var course = courseRepository.findById(absModuleDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "course_id", absModuleDTO.getCourseId()));
        final var modules = absModuleDTO.getModules();

        abstractModule.setModules(modules);
        abstractModule.setName(course.getName() + " " + modules + "ta modul");
        abstractModule.setCourse(course);

        return abstractModule;
    }
}