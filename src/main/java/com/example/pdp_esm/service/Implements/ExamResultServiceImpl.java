package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.DeleteRequestDTO;
import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.result.*;
import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.entity.requests.DeleteExamResultRequest;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.ExamResultService;
import com.example.pdp_esm.repository.deleteRequests.DeleteExamResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final DeleteExamResultRepository deleteExamResultRepository;
    private final StudentRepository studentRepository;
    private final QuestionRepository questionRepository;

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
        Optional<ExamResult> optionalExamResult = Optional.ofNullable(examResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "id", id)));
        ExamResult examResult = optionalExamResult.get();

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
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", result.getId())));
        Student student = optionalStudent.get();

        List<Long> questionsIdList = resultDTO.getQuestionsIdList();
//        List<Question> questionList = questionsIdList.stream().map(questionRepository::getById).collect(Collectors.toList());
        List<Question> questionList = questionsIdList.stream()
                .map(questionId -> questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId)))
                .filter(Objects::nonNull)
                .toList();

        result.setStudent(student);
        result.setScore(resultDTO.getScore());
        result.setQuestionList(questionList);
        result.setResultType(resultDTO.getResultType());
        final var examResult = examResultRepository.save(result); //null
        return examResult;
    }

    public ResExamResults toResExamResultDTO(ExamResult examResult) {

        Student student = examResult.getStudent();
        Group group = examResult.getStudent().getGroup();

        return ResExamResults.builder()
                .score(examResult.getScore())
                .resultType(String.valueOf(examResult.getResultType()))
                .studentInfo(ResPaymentStudentInfo.builder()
                        .studentName(student.getFullName())
                        .studentGroupName(group.getGroupName())
                        .studentPhoneNumber(student.getPhoneNumber())
                        .build())
                .questionList(examResult.getQuestionList())
                .build();
    }

    public ResDeleteExamResultDTO toResDeleteExamResultDTO(DeleteExamResultRequest deleteExamResultRequest){
        return ResDeleteExamResultDTO.builder()
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