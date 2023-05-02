package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.GroupDTO;

public interface GroupService {

    ApiResponse<?> createGroup(GroupDTO groupDTO);

    ApiResponse<?> getAllGroups();

    ApiResponse<?> getOneGroup(Long group_id);

    ApiResponse<?> updateGroup(Long group_id, GroupDTO groupDTO);

    ApiResponse<?> deleteGroup(Long group_id);

    ApiResponse<?> getAllActiveFalseGroups();

    ApiResponse<?> getOneActiveFalseGroup(Long group_id);

}