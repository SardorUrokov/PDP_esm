package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.GroupDTO;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public ApiResponse<?> createGroup(GroupDTO groupDTO) {
        return null;
    }

    @Override
    public ApiResponse<?> getAllGroups() {
        return null;
    }

    @Override
    public ApiResponse<?> getOneGroup(Long group_id) {
        return null;
    }

    @Override
    public ApiResponse<?> updateGroup(Long group_id, GroupDTO groupDTO) {
        return null;
    }

    @Override
    public ApiResponse<?> deleteGroup(Long group_id) {
        return null;
    }

    @Override
    public ApiResponse<?> getAllActiveFalseGroups() {
        return null;
    }

    @Override
    public ApiResponse<?> getOneActiveFalseGroup(Long group_id) {
        return null;
    }
}
