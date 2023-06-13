package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ModulesRepository extends JpaRepository<Modules, Long> {

    Optional<Modules> findByAbstractModule_Course_Id(Long course_id);

    Optional<Modules> findByOrdinalNumber(Long ordinalNumber);

    @Query("select m from Modules m " +
            "inner join GroupModule gm " +
            "where gm.group.id = :groupId " +
            "and m.ordinalNumber = :ordinalNumber " +
            "and m.abstractModule.id = :absModuleId"
    )
    Optional<Modules> findByCourseIdAndOrdinalNumberAndGroupId(Long absModuleId, Long ordinalNumber, Long groupId);

    boolean existsByAbstractModule_IdAndOrdinalNumber(Long course_id, Long ordinalNumber);
}