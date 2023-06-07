package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.ExaminingDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.AttemptDTO;
import com.example.pdp_esm.dto.result.ResExamResults;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.exception.ResourceNotFoundException;
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
            boolean equals, equals1, equals2, equals3, equals4;

            for (AttemptDTO attempt : attempts)
            {
                final var attemptQuestion = attempt.getQuestion();
                final var byQuestion = questionRepository.findByQuestionText(attemptQuestion)
                        .orElseThrow(()-> new ResourceNotFoundException("Question", "question", attemptQuestion));

                questionsIdsList.add(byQuestion.getId());

                List<String> selectedAnswers = attempt.getSelectedAnswer();
                if (!byQuestion.getQuestionType().equals(QuestionType.SEQUENCE)) {
                    if (byQuestion.getAnswer().getTrue_answer()
                            .equals(
                                    selectedAnswers.get(0)))
                        score += 10;
                } else {
                    equals = selectedAnswers.get(0).equals(byQuestion.getAnswer().getTrue_answer());
                    equals1 = selectedAnswers.get(1).equals(byQuestion.getAnswer().getAnswer1());
                    equals2 = selectedAnswers.get(2).equals(byQuestion.getAnswer().getAnswer2());
                    equals3 = selectedAnswers.get(3).equals(byQuestion.getAnswer().getAnswer3());
                    equals4 = selectedAnswers.get(4).equals(byQuestion.getAnswer().getAnswer4());

                    if (equals && equals1 && equals2 && equals3 && equals4) {
                        score += 10;
                    }
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

            ResExamResults resExamResults =
                    new ResExamResults(data.getScore(), data.getStudentInfo(), data.getResultType(), data.getSubmitted_time(), data.getQuestionList());

            return ApiResponse.builder()
                    .message(message)
                    .success(true)
                    .data(resExamResults)
                    .build();
        }
    }
}