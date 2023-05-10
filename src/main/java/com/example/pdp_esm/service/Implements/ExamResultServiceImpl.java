package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final QuestionRepository questionRepository;

    @Override
    public ApiResponse<?> createExamResult(ExamResultDTO examResultDTO) {

        ExamResult result = new ExamResult();
        ExamResult save = settingValues(result, examResultDTO);

        return ApiResponse.builder()
                .message("Result saved!")
                .success(true)
                .data(save)
                .build();
    }

    @Override
    public ApiResponse<?> getAllExamResult() {
        List<ExamResult> examResults = examResultRepository.findAll();

        return ApiResponse.builder()
                .message("Result List!")
                .success(true)
                .data(examResults)
                .build();
    }

    @Override
    public ApiResponse<?> getOneExamResult(Long result_id) {
        Optional<ExamResult> optionalResult = Optional.ofNullable(examResultRepository.findById(result_id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam Result", "id", result_id)));
        ExamResult examResult = optionalResult.get();

        return ApiResponse.builder()
                .message("Result with " + result_id + " id")
                .success(true)
                .data(examResult)
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
                .data(save)
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
        List<Question> questionList = questionsIdList.stream().map(questionRepository::getById).toList();

        result.setStudent(student);
        result.setScore(resultDTO.getScore());
        result.setQuestionList(questionList);
        result.setResultType(resultDTO.getResultType());
        return examResultRepository.save(result);
    }
}
