package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.GroupModule;
import com.example.pdp_esm.entity.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModulesRepository extends JpaRepository<Modules, Long> {
    Optional<Modules> findByCourse_Id(Long course_id);

    Optional<Modules> findByOrdinalNumber(Integer ordinalNumber);

    List<Modules> findByEduModulesWithin(List<GroupModule> eduModules);
}