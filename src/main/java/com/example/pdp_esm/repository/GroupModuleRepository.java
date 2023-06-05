package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.GroupModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupModuleRepository extends JpaRepository<GroupModule, Long> {
    boolean existsByGroup_Id(Long group_id);

    List<GroupModule> getByGroup_Id(Long group_id);
}