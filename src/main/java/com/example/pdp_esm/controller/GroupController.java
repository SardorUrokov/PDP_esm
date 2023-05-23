package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.GroupDTO;
import com.example.pdp_esm.service.Implements.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupServiceImpl groupService;

    @PostMapping("/create")
    public ResponseEntity<?> createGroups(@RequestBody GroupDTO groupDTO){
        ApiResponse<?> response = groupService.createGroup(groupDTO);

        if (response.isSuccess()) log.warn("Creating Group! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllGroups(@RequestParam(value = "course", defaultValue = "") String courseName, @RequestParam(defaultValue = "") String search) {

        ApiResponse<?> response = groupService.getAllGroups(courseName, search);
        log.warn("Getting All Groups List! -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneGroup(@PathVariable Long id){
        ApiResponse<?> response = groupService.getOneGroup(id);

        if (response.isSuccess()) log.warn("Getting Course {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO){
        ApiResponse<?> response = groupService.updateGroup(id, groupDTO);

        if (response.isSuccess()) log.warn("Group Updated {} with id! -> {}", response.getData(), id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id){
        ApiResponse<?> response = groupService.deleteGroup(id);

        if (response.isSuccess()) log.warn("Group Terminated {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/terminated")
    public ResponseEntity<?> getAllTerminatedGroups(){
        ApiResponse<?> response = groupService.getAllActiveFalseGroups();
        log.warn("Getting All Terminated Groups! -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/terminated/{id}")
    public ResponseEntity<?> getOneTerminatedGroup(@PathVariable Long id){
        ApiResponse<?> response = groupService.getOneActiveFalseGroup(id);
        if (response.isSuccess()) log.warn("Get One Terminated Group {} with id! -> {}", response.getData(), id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}