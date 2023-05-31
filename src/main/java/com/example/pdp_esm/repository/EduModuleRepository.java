package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.GroupModule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EduModuleRepository extends JpaRepository<GroupModule, Long> {

//    Optional<ModulePerGroup> findByGroupsIn(Collection<List<Group>> groups);
}