package com.example.pdp_esm.service.Implements;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.pdp_esm.entity.*;
import lombok.RequiredArgsConstructor;
import com.example.pdp_esm.dto.result.*;
import com.example.pdp_esm.dto.ExamResultDTO;
import org.springframework.stereotype.Service;
import com.example.pdp_esm.dto.DeleteRequestDTO;
import com.example.pdp_esm.service.ExamResultService;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.ExamineInfoRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.entity.requests.DeleteExamResultRequest;
import com.example.pdp_esm.repository.deleteRequests.DeleteExamResultRepository;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final StudentRepository studentRepository;
    private final QuestionServiceImpl questionService;
    private final QuestionRepository questionRepository;
    private final ExamResultRepository examResultRepository;
//    private final ExamineInfoServiceImpl examineInfoService;
    private final ExamineInfoRepository examineInfoRepository;
    private final DeleteExamResultRepository deleteExamResultRepository;

    @Override
    public ApiResponse<?> createExamResult(ExamResultDTO examResultDTO) {

        ExamResult result = new ExamResult();
        ExamResult save = settingValues(result, examResultDTO);
        return ApiResponse.builder()
                .message("Result saved!")
                .success(true)
                .data(toResExamResultDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> getAllExamResult() {

        List<ExamResult> examResults = examResultRepository.findAll();

        return ApiResponse.builder()
                .message("Result List!")
                .success(true)
                .data(toResExamResultDTOList(examResults))
                .build();
    }

    @Override
    public ApiResponse<?> getOneExamResult(Long result_id) {
        Optional<ExamResult> optionalResult = Optional.ofNullable(examResultRepository.findById(result_id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "id", result_id)));
        ExamResult save = optionalResult.get();

        return ApiResponse.builder()
                .message("Result with " + result_id + " id")
                .success(true)
                .data(toResExamResultDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> updateExamResult(Long result_id, ExamResultDTO resultDTO) {

        Optional<ExamResult> optionalExamResult = Optional.ofNullable(examResultRepository.findById(result_id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "id", result_id)));

        ExamResult result = optionalExamResult.get();
        ExamResult save = settingValues(result, resultDTO);

        return ApiResponse.builder()
                .message("ExamResult Updated!")
                .success(true)
                .data(toResExamResultDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> deleteExamResult(Long id) {
//         find examResult in examResultRepository through examResult_id
//         then bring it to deleteExamResultRepository and examResultRepository.delete()
        ExamResult examResult = examResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "id", id));

        deleteExamResultRepository.deleteByExamResult_Id(examResult.getId());
        examResultRepository.delete(examResult);

        return
                new ApiResponse<>("ExamResult with " + id + " is Deleted!", true);
    }

    public ApiResponse<?> createDeleteRequest(DeleteRequestDTO deleteRequestDTO) {
        Optional<ExamResult> optionalExamResult = examResultRepository.findById(deleteRequestDTO.getData_id());
        ExamResult examResult = optionalExamResult.get();

        DeleteExamResultRequest deleteExamResult = new DeleteExamResultRequest();
        deleteExamResult.setExamResult(examResult);

        if (deleteRequestDTO.getDescription().isEmpty())
            return new ApiResponse<>("Description is empty!", false);

        deleteExamResult.setDescription(deleteRequestDTO.getDescription());
        deleteExamResult.setCreatedAt(new Date());
        deleteExamResult.setActive(true);

        DeleteExamResultRequest save =
                deleteExamResultRepository.save(deleteExamResult);
        return
                new ApiResponse<>("Delete ExamResult Request created!", true, toResDeleteExamResultDTO(save));
    }

    public ApiResponse<?> getAllDeleteExamResultRequests() {
        List<DeleteExamResultRequest> deletePaymentRequestList = deleteExamResultRepository.findAll();

        return ApiResponse.builder()
                .message("Delete ExamResult Requests LIst ")
                .success(true)
                .data(toResDeleteExamResultDTOList(deletePaymentRequestList))
                .build();
    }

    public ExamResult settingValues(ExamResult result, ExamResultDTO resultDTO) {

        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findById(resultDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", resultDTO.getStudentId())));
        Student student = optionalStudent.get();

        List<Long> questionsIdList = resultDTO.getQuestionsIdList();
        List<Question> questionList = questionsIdList.stream()
                .map(questionId -> questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId)))
                .filter(Objects::nonNull)
                .toList();

        final var examineInfoId = resultDTO.getExamineInfoId();
        final var examineInfo = examineInfoRepository.findById(examineInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Examine Info ", "examineInfo_id", examineInfoId));

        result.setStudent(student);
        result.setScore(resultDTO.getScore());
        result.setExamineInfo(examineInfo);
        result.setQuestionList(questionList);
        result.setResultType(resultDTO.getResultType());
        result.setCreatedAt(new Date());
        return examResultRepository.save(result);
    }

    public ResExamResults toResExamResultDTO(ExamResult examResult) {

        Student student = examResult.getStudent();
        Group group = examResult.getStudent().getGroup();

//        final var resExamineInfoDTO = examineInfoService.toResExamineInfoDTO(examResult.getExamineInfo());

        return ResExamResults.builder()
                .examResult_id(examResult.getId())
                .score(examResult.getScore())
//                .resExamineInfoDTO(resExamineInfoDTO)
                .resultType(String.valueOf(examResult.getResultType()))
                .submitted_time(examResult.getCreatedAt().toString())
                .studentInfo(ResPaymentStudentInfo.builder()
                        .studentName(student.getFullName())
                        .studentGroupName(group.getGroupName())
                        .studentPhoneNumber(student.getPhoneNumber())
                        .build())
                .questionList(questionService
                        .toResQuestionDTOList(examResult.getQuestionList()))
                .build();
    }

    public ResDeleteExamResultDTO toResDeleteExamResultDTO(DeleteExamResultRequest deleteExamResultRequest) {
        return ResDeleteExamResultDTO.builder()
                .id(deleteExamResultRequest.getId())
                .description(deleteExamResultRequest.getDescription())
                .requestCreated_time(deleteExamResultRequest.getCreatedAt().toString())
                .active(deleteExamResultRequest.getActive())
                .examResults(toResExamResultDTO(deleteExamResultRequest.getExamResult()))
                .build();
    }

    public List<ResExamResults> toResExamResultDTOList(List<ExamResult> examResults) {
        return examResults.stream().map(this::toResExamResultDTO).toList();
    }

    public List<ResDeleteExamResultDTO> toResDeleteExamResultDTOList(List<DeleteExamResultRequest> deleteExamResultRequestList) {
        return deleteExamResultRequestList.stream().map(this::toResDeleteExamResultDTO).toList();
    }
}