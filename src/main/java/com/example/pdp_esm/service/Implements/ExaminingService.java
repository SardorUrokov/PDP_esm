package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.ExaminingDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.AttemptDTO;
import com.example.pdp_esm.dto.result.ResExamResults;
import com.example.pdp_esm.dto.test.AnswerDTO;
import com.example.pdp_esm.dto.test.AnswerObject;
import com.example.pdp_esm.dto.test.CheckingAttemptsDTO;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.test.AnswerTest;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.repository.test.AnswerRepositoryTest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminingService {

    private final AnswerRepositoryTest answerRepository;
    private final QuestionRepository questionRepository;
    private final ExamResultServiceImpl examResultService;
    private final ExamResultRepository examResultRepository;

//    public ApiResponse<?> calculateResult(ExaminingDTO examiningDTO) {
//
//        final var existsByStudentId = examResultRepository.existsByStudentId(examiningDTO.getStudent_id());
//        if (existsByStudentId)
//            return new ApiResponse<>("Exam Result with this " + examiningDTO.getStudent_id() + " id Student is Already saved! ", false);
//        else {
//            List<AttemptDTO> attempts = examiningDTO.getAttempts();
//            List<Long> questionsIdsList = new ArrayList<>();
//
//            int score = 0;
//            String message = "Student Failed";
//            boolean equals, equals1, equals2, equals3, equals4;
//
//            for (AttemptDTO attempt : attempts) {
//                final var attemptQuestion = attempt.getQuestion();
//                final var question = questionRepository.findByQuestionText(attemptQuestion)
//                        .orElseThrow(() -> new ResourceNotFoundException("Question", "question body", attemptQuestion));
//
//                questionsIdsList.add(question.getId());
//
//                List<String> selectedAnswers = attempt.getSelectedAnswer();
//                if (!question.getQuestionType().equals(QuestionType.SEQUENCE)) {
//                    if (question.getAnswer().getTrue_answer()
//                            .equals(selectedAnswers.get(0))) {
//                        score += 10;
//                    }
//                } else {
//                    equals = selectedAnswers.get(0).equals(question.getAnswer().getTrue_answer());
//                    equals1 = selectedAnswers.get(1).equals(question.getAnswer().getAnswer1());
//                    equals2 = selectedAnswers.get(2).equals(question.getAnswer().getAnswer2());
//                    equals3 = selectedAnswers.get(3).equals(question.getAnswer().getAnswer3());
//                    equals4 = selectedAnswers.get(4).equals(question.getAnswer().getAnswer4());
//
//                    if (equals && equals1 && equals2 && equals3 && equals4) {
//                        score += 10;
//                    }
//                }
//            }
//
//            ExamResultDTO examResultDTO = new ExamResultDTO();
//            examResultDTO.setScore(score);
//            examResultDTO.setStudentId(examiningDTO.getStudent_id());
//            examResultDTO.setQuestionsIdList(questionsIdsList);
//
//            if (score >= 60) {
//                examResultDTO.setResultType(ResultType.SUCCESS);
//                message = "Student SUCCEED!";
//            } else
//                examResultDTO.setResultType(ResultType.FAIL);
//
//            ApiResponse<?> examResult = examResultService.createExamResult(examResultDTO);
//            ResExamResults data = (ResExamResults) examResult.getData();
//
//            ResExamResults resExamResults = new ResExamResults(
//                    data.getScore(),
//                    data.getStudentInfo(),
//                    data.getResultType(),
//                    data.getSubmitted_time(),
//                    data.getQuestionList()
//            );
//
//            return ApiResponse.builder()
//                    .message(message)
//                    .success(true)
//                    .data(resExamResults)
//                    .build();
//        }
//    }

//    public ApiResponse<?> checkingAnswers(CheckingAttemptsDTO checkingAttemptsDTO) {
//
//        final var studentId = checkingAttemptsDTO.getStudent_id();
//        final var existsByStudentId = examResultRepository.existsByStudentId(studentId);
//
//        if (existsByStudentId)
//            return
//                    new ApiResponse<>("Exam Result with this " + studentId + " student_id is Already saved! ", false);
//        else {
//
//            List<AnswerObject> selectedAnswers = checkingAttemptsDTO.getSelectedAnswers();
//            List<Long> questionsIdsList = new ArrayList<>();
//            String message = "Student Failed";
//            int score = 0;
//
//            for (AnswerObject selectedAnswer : selectedAnswers) {
//
//                final var questionId = selectedAnswer.getQuestion_id();
//                final var exists = questionRepository.existsById(questionId);
//                if (exists) questionsIdsList.add(questionId);
//
//                final var question = questionRepository.findById(questionId)
//                        .orElseThrow(() -> new ResourceNotFoundException("Question", "question_id", questionId));
//                final var questionType = question.getQuestionType();
//
//                final var optionalAnswer = answerRepository
//                        .findByQuestion_IdAndStatusTrue(questionId)
//                        .orElseThrow(() -> new ResourceNotFoundException("Answer", "question_id", questionId));
//
//                if (questionType.equals(QuestionType.SEQUENCE)) {
//
//                    final var answers = answerRepository.findByQuestion_IdOrderByPosition(questionId);
//                    for (int i = 0; i < answers.size(); i++) {
//                        //sequence bo'yicha
//                        //listni aylanib answerlarni positioni boyicha check qilish
//                    }
//                } else if (questionType.equals(QuestionType.WRITE_MISSING_WORD)) {
//                    // answerni get bodysi b-n inputni equalsIgnoreCase qilish k-k
//                } else {
//                    final var answerBody = optionalAnswer.getBody();
//                    final var answerInput = selectedAnswer.getInput();
//
//                    if (selectedAnswer.isStatus() && answerBody.equals(answerInput))
//                        score += 10;
//                }
//            }
////            ExamResultDTO examResultDTO = new ExamResultDTO();
////            examResultDTO.setScore(score);
////            examResultDTO.setStudentId(checkingAttemptsDTO.getStudent_id());
////            examResultDTO.setQuestionsIdList(questionsIdsList);
////
////            if (score >= 60) {
////                examResultDTO.setResultType(ResultType.SUCCESS);
////                message = "Student SUCCEED!";
////            } else
////                examResultDTO.setResultType(ResultType.FAIL);
////
////            ApiResponse<?> examResult = examResultService.createExamResult(examResultDTO);
////            ResExamResults data = (ResExamResults) examResult.getData();
////
////            ResExamResults resExamResults = new ResExamResults(
////                    data.getScore(),
////                    data.getStudentInfo(),
////                    data.getResultType(),
////                    data.getSubmitted_time(),
////                    data.getQuestionList()
////            );
//            return ApiResponse.builder()
//                    .message(message)
//                    .success(true)
//                    .data(null) //resExamResults
//                    .build();
//
//        }
//    }


    public ApiResponse<?> checking(CheckingAttemptsDTO checkingAttemptsDTO) {

        final var studentId = checkingAttemptsDTO.getStudent_id();
        final var existsByStudentId = examResultRepository.existsByStudentId(studentId);

        if (existsByStudentId)
            return
                    new ApiResponse<>("Exam Result with this " + studentId + " student_id is Already saved! ", false);
        else {

            List<AnswerObject> selectedAnswers = checkingAttemptsDTO.getSelectedAnswers();
            List<Long> questionsIdsList = new ArrayList<>();
            String message = "Student Failed";
            int score = 0;

            for (int i = 0; i < selectedAnswers.size(); i++) {

                final var answerObject = selectedAnswers.get(i);
                final var questionType = answerObject.getQuestionType();
                final var answerDTOList = answerObject.getAnswerDTOS();

                final var answerDTO = answerDTOList.get(0);
                final var questionId = answerDTO.getQuestion_id();
                questionsIdsList.add(questionId);

                if (questionType.equals(String.valueOf(QuestionType.WRITE_MISSING_WORD))) {

                    final var answerTestList = answerRepository.findByQuestion_IdOrderByPosition(questionId);
                    final var answerTest = answerTestList.get(0);

                    final var answerBody = answerTest.getBody();
                    final var answerDTOInput = answerDTO.getInput();

                    if (answerBody.equalsIgnoreCase(answerDTOInput) && answerDTO.getStatus().equals(true))
                        score += 10;

                } else if (questionType.equals(String.valueOf(QuestionType.SEQUENCE))) {

                    final var answerTestList = answerRepository.findByQuestion_IdOrderByPosition(questionId);

                    int k = 1;
                    for (int j = 0; j < answerDTOList.size(); j++) {
                        if (answerDTOList.get(k).getInput().equalsIgnoreCase(answerTestList.get(j + 1).getBody()))
                            score += 10;
                    }
                } else if (questionType.equals(String.valueOf(QuestionType.TEST))){


                }
            }

            return ApiResponse.builder()
                    .message(message)
                    .success(true)
                    .data(null) //resExamResults
                    .build();
        }
    }
}