package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ModulesRepository extends JpaRepository<Modules, Long> {

    Optional<Modules> findByAbstractModule_Course_Id(Long course_id);

    Optional<Modules> findByOrdinalNumber(Long ordinalNumber);

    boolean existsByAbstractModule_IdAndOrdinalNumber(Long course_id, Long ordinalNumber);
}