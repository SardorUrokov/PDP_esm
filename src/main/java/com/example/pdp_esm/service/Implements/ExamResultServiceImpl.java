package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.result.*;
import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final StudentServiceImpl studentService;
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
                .message("Result Updated!")
                .success(true)
                .data(toResExamResultDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> deleteExamResult(Long id) {
        return ApiResponse.builder()
                .message("Delete Result Method Not Created yet!")
                .success(false)
                .build();
    }

    public ExamResult settingValues(ExamResult result, ExamResultDTO resultDTO) {

        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findById(resultDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", result.getId())));
        Student student = optionalStudent.get();

        List<Long> questionsIdList = resultDTO.getQuestionsIdList();
        List<Question> questionList = questionsIdList.stream().map(questionRepository::getById).collect(Collectors.toList());

        result.setStudent(student);
        result.setScore(resultDTO.getScore());
        result.setQuestionList(questionList);
        result.setResultType(resultDTO.getResultType());
        return examResultRepository.save(result);
    }

    public ResExamResults toResExamResultDTO (ExamResult examResult){

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


    public List<ResExamResults> toResExamResultDTOList(List<ExamResult> examResults) {
        return examResults.stream().map(this::toResExamResultDTO).toList();
    }
}
