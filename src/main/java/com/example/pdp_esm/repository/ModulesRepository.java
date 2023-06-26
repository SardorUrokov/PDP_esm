package com.example.pdp_esm.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.example.pdp_esm.entity.GroupModule;
import com.example.pdp_esm.entity.Modules;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModulesRepository extends JpaRepository<Modules, Long> {

    Optional<Modules> findByAbstractModule_Course_Id(Long course_id);

    Optional<Modules> findByOrdinalNumber(Long ordinalNumber);

    @Query("select m from Modules m " +
            "inner join GroupModule gm " +
            "where gm.group.id = :groupId " +
            "and m.ordinalNumber = :ordinalNumber " +
            "and m.abstractModule.id = :absModuleId"
    )
    Optional<Modules> findByAbsModuleIdAndOrdinalNumberAndGroupId(Long absModuleId, Long ordinalNumber, Long groupId);

    @Query("select m from Modules m " +
            "inner join GroupModule gm " +
            "where gm.group.id = :groupId " +
            "and m.abstractModule.course.id = :courseId"
    )
    List<Modules> findByCourseIdAndGroupIdOrderByOrdinalNumberDesc(Long courseId, Long groupId);

    boolean existsByAbstractModule_IdAndOrdinalNumber(Long course_id, Long ordinalNumber);

    boolean existsByGroupModulesIn(List<GroupModule> groupModules);

    Optional<Modules> findByGroupModulesIn(List<GroupModule> groupModules);
}