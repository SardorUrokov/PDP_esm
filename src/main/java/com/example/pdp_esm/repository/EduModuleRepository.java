package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.EduModule;
import com.example.pdp_esm.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EduModuleRepository extends JpaRepository<EduModule, Long> {

    Optional<EduModule> findByGroupsIn(Collection<List<Group>> groups);
}