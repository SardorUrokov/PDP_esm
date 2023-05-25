package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.ExaminingDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.AttemptDTO;
import com.example.pdp_esm.dto.result.ResExamResults;
import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminingService {

    private final QuestionRepository questionRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamResultServiceImpl examResultService;

    public ApiResponse<?> calculateResult(ExaminingDTO examiningDTO) {

        final var existsByStudentId = examResultRepository.existsByStudentId(examiningDTO.getStudent_id());
        if (existsByStudentId)
            return new ApiResponse<>("Exam Result with this " + examiningDTO.getStudent_id() + " id Student is Already saved! ", false);
        else {
            List<AttemptDTO> attempts = examiningDTO.getAttempts();
            List<Long> questionsIdsList = new ArrayList<>();
            ExamResultDTO examResultDTO = new ExamResultDTO();

            int score = 0;
            String message = "Student Failed";

            for (AttemptDTO attempt : attempts) {
                final var byQuestion = questionRepository.findByQuestion(attempt.getQuestion());
                questionsIdsList.add(byQuestion.get().getId());
                if (byQuestion.get().getTrue_answer().equals(attempt.getSelectedAnswer())) {
                    score += 10;
                }
            }
            examResultDTO.setScore(score);
            examResultDTO.setStudentId(examiningDTO.getStudent_id());
            examResultDTO.setQuestionsIdList(questionsIdsList);

            if (score >= 60) {
                examResultDTO.setResultType(ResultType.SUCCESS);
                message = "Student SUCCEED!";
            } else
                examResultDTO.setResultType(ResultType.FAIL);

            ApiResponse<?> examResult = examResultService.createExamResult(examResultDTO);
            ResExamResults data = (ResExamResults) examResult.getData();

//            ExamResult result = new ExamResult(
//                    data.getScore(), data.getStudent(), data.getResultType(), data.getQuestionList()
//            );
           
            ResExamResults resExamResults = new ResExamResults(data.getScore(), data.getStudentInfo(), data.getResultType(), data.getSubmitted_time(), data.getQuestionList());
//            ResExamResults resExamResultDTO = examResultService.toResExamResultDTO(data);
            
            return ApiResponse.builder()
                    .message(message)
                    .success(true)
                    .data(resExamResults)
                    .build();
        }
    }
}