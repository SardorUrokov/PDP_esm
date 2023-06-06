package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.AbstractModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbstractModuleRepository extends JpaRepository<AbstractModule, Long> {

    boolean existsByCourseIdAndModules(Long course_id, Long modules);

    Optional<AbstractModule> findByCourse_Id(Long course_id);

    Optional<AbstractModule> findByModules(Long module);
}