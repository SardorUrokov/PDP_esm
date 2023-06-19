package com.example.pdp_esm.service.Implements;

import java.util.Objects;
import java.util.Set;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.dto.ExamineInfoDTO;
import com.example.pdp_esm.entity.ExamineInfo;
import com.example.pdp_esm.entity.enums.ExamType;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.ExamineInfoService;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.dto.result.ResExamineInfoDTO;
import com.example.pdp_esm.repository.ExamineInfoRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamineInfoServiceImpl implements ExamineInfoService {

    private final GroupServiceImpl groupService;
    private final CourseServiceImpl courseService;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final ExamineInfoRepository examineInfoRepository;

    @Override
    public ApiResponse<?> create(ExamineInfoDTO dto) {

        ExamineInfo examineInfo = new ExamineInfo();
        final var save = settingValues(examineInfo, dto);
        final var exists = examineInfoRepository
                .existsByStartsDateAndCoursesInAndGroupsIn(
                        save.getStartsDate(),
                        save.getCourses(),
                        save.getGroups());

        if (!exists) {
            examineInfoRepository.save(save);
            return ApiResponse.builder()
                    .message("Examine Info Saved!")
                    .success(true)
                    .data(toResExamineInfoDTO(examineInfo))
                    .build();
        } else return
                new ApiResponse<>(
                        "Such a Examine Info Object is already saved!",
                        false
                );
    }

    @Override
    public ApiResponse<?> readAll() {

        final var examineInfoList = examineInfoRepository.findAll();
        return ApiResponse.builder()
                .message("Examine Info List")
                .success(true)
                .data(toResExamineInfoDTOList(examineInfoList))
                .build();
    }

    @Override
    public ApiResponse<?> readOne(Long id) {

        final var examineInfo = examineInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examine Info Object", "id", id));

        return ApiResponse.builder()
                .message("Examine Info by " + id + " id")
                .success(true)
                .data(toResExamineInfoDTO(examineInfo))
                .build();
    }

    @Override
    public ApiResponse<?> byStartsDate(String date) {

        final var parsedDate = parsing(date);
        final var examineInfo = examineInfoRepository.findByStartsDate(parsedDate)
                .orElseThrow(() -> new ResourceNotFoundException("Examine Object Info", "startsDate", parsedDate));

        return ApiResponse.builder()
                .message("Examine Info by StartDate " + parsedDate)
                .success(true)
                .data(toResExamineInfoDTO(examineInfo))
                .build();
    }

    @Override
    public ApiResponse<?> byExamType(String type){

        final var examType = ExamType.valueOf(type);
        final var examineInfos = examineInfoRepository.findByExamType(examType);

        return ApiResponse.builder()
                .message("Examine Info by " + type + " Type")
                .success(true)
                .data(toResExamineInfoDTOList(examineInfos))
                .build();
    }

    @Override
    public ApiResponse<?> update(Long id, ExamineInfoDTO dto) {

        final var byId = examineInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamineInfo Object", "id", id));

        final var examineInfo = settingValues(byId, dto);
        examineInfoRepository.save(examineInfo);

        return ApiResponse.builder()
                .message("Examine Info Object is Updated!")
                .success(true)
                .data(toResExamineInfoDTO(examineInfo))
                .build();
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        final var exists = examineInfoRepository.existsById(id);
        if (!exists)
            return new ApiResponse<>("Examine Info Object not found!", false);
        else {
            examineInfoRepository.deleteById(id);
            return new ApiResponse<>("Examine Info Object is Deleted!", true);
        }
    }

    public ExamineInfo settingValues(ExamineInfo examineInfo, ExamineInfoDTO examineInfoDTO) {

        final var coursesIds = examineInfoDTO.getCoursesIds();
        final var groupsIds = examineInfoDTO.getGroupsIds();

        Set<Course> courses =
                coursesIds.stream().map(courseRepository::getById).collect(Collectors.toSet());

        Set<Group> groups = groupsIds.stream()
                .map(groupId -> groupRepository.findById(groupId)
                        .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId)))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        String examName = createExamName(examineInfoDTO.getExamType(), groups);

        examineInfo.setExamName(examName);
        examineInfo.setAttemptsLimit(examineInfoDTO.getAttempts());
        examineInfo.setNumOfQuestions(examineInfoDTO.getNumOfQuestions());
        examineInfo.setStartsDate(parsing(examineInfoDTO.getStartsDate()));
        examineInfo.setExamType(ExamType.valueOf(examineInfoDTO.getExamType()));
        examineInfo.setCourses(courses);
        examineInfo.setGroups(groups);

        return examineInfo;
    }

    private static String createExamName(String examType, Set<Group> groups) {
        StringBuilder examNameBuilder = new StringBuilder();
        examNameBuilder.append(examType).append("-");

        for (Group group : groups) {
            examNameBuilder.append(group.getGroupName()).append("_");
        }

        examNameBuilder.setLength(examNameBuilder.length() - 1);

        return examNameBuilder.toString();
    }

    public Date parsing(String dateTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        Date date = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            date = formatter.parse(dateTime);
            log.info("Parsed date: " + date);

        } catch (ParseException e) {
            log.error("Failed to parse the date: " + e.getMessage());
        }
        return date;
    }

    public ResExamineInfoDTO toResExamineInfoDTO(ExamineInfo examineInfo) {
        List<Group> groupList = examineInfo.getGroups().stream().toList();

        return ResExamineInfoDTO.builder()
                .attempts(examineInfo.getAttemptsLimit())
                .numOfQuestions(examineInfo.getNumOfQuestions())
                .examName(examineInfo.getExamName())
                .startsDate(examineInfo.getStartsDate().toString())
                .examType(examineInfo.getExamType().toString())
                .coursesWithGroups(courseService.toDTOSet(examineInfo.getCourses()))
                .groupDTOS(groupService.toDTOList(groupList))
                .build();
    }

    public List<ResExamineInfoDTO> toResExamineInfoDTOList(List<ExamineInfo> examineInfos) {
        return examineInfos.stream().map(this::toResExamineInfoDTO).collect(Collectors.toList());
    }
}