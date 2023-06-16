package com.example.pdp_esm.service.Implements;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.pdp_esm.repository.*;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.test.AnswerObject;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.dto.result.ResExamResults;
import com.example.pdp_esm.dto.test.CheckingAttemptsDTO;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.test.AnswerRepositoryTest;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ExaminingService {

    private final StudentRepository studentRepository;
    private final AnswerRepositoryTest answerRepository;
    private final QuestionRepository questionRepository;
    private final ExamResultServiceImpl examResultService;
    private final ExamResultRepository examResultRepository;
    private final ExamineInfoRepository examineInfoRepository;
    private final ReserveUsersRepository reserveUsersRepository;

    public ApiResponse<?> checkingAnswers(CheckingAttemptsDTO checkingAttemptsDTO) {

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

            for (final AnswerObject answerObject : selectedAnswers) {

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
                    for (int j = 1; j < answerDTOList.size(); j++) {
                        if (answerDTOList.get(k).getInput().equalsIgnoreCase(answerTestList.get(j).getBody()))
                            score += 10;
                    }
                } else if ((questionType.equals(String.valueOf(QuestionType.TEST)))
                        ||
                        questionType.equals(String.valueOf(QuestionType.TRUE_FALSE))) {

                    final var questionByStatusTrue = answerRepository.findByQuestion_IdAndStatusTrue(questionId)
                            .orElseThrow(() -> new ResourceNotFoundException("Status TRUE Question", "question_id", questionId));

                    if (questionByStatusTrue.getBody().equalsIgnoreCase(answerDTO.getInput()))
                        score += 10;
                }
            }

            ExamResultDTO examResultDTO = new ExamResultDTO();
            examResultDTO.setScore(score);
            examResultDTO.setStudentId(checkingAttemptsDTO.getStudent_id());
            examResultDTO.setQuestionsIdList(questionsIdsList);

            if (score >= 60) {
                examResultDTO.setResultType(ResultType.SUCCESS);
                message = "Student SUCCEED!";
            } else
                examResultDTO.setResultType(ResultType.FAIL);

            ApiResponse<?> examResult = examResultService.createExamResult(examResultDTO);
            ResExamResults data = (ResExamResults) examResult.getData();

            ResExamResults resExamResults = new ResExamResults(
                    data.getScore(),
                    data.getStudentInfo(),
                    data.getResultType(),
                    data.getSubmitted_time(),
                    data.getQuestionList()
            );

            return ApiResponse.builder()
                    .message(message)
                    .success(true)
                    .data(resExamResults)
                    .build();
        }
    }

    public ApiResponse<?> getExamQuestions(String otp) {
        List<Question> questionList;

        final var user = reserveUsersRepository.findByOtpCode(otp)
                .orElseThrow(() -> new ResourceNotFoundException("User", "otp", otp));
        final var usersDType = user.getDType();

        if (usersDType.equals("Student")) {

            final var student = studentRepository.findByPhoneNumber(user.getPhoneNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", user.getPhoneNumber()));
            final var studentGroup = student.getGroup();

//            final var examineInfo = examineInfoRepository.findByGroupsIn(Collections.singleton(studentGroup))
//                    .orElseThrow(() -> new ResourceNotFoundException("Examine Info", "group", studentGroup));
//            final var numOfQuestions = examineInfo.getNumOfQuestions();

            final var courseId = studentGroup.getCourse().getId();

            questionList = rndQuestions(courseId, 10); // it returns the number of questions set in numOfQuestions field

        } else {
            return new ApiResponse<>("User is not a Student", false);
        }

        return ApiResponse.builder()
                .message("Exam Questions!")
                .success(true)
                .data(questionList)
                .build();
    }

    public List<Question> rndQuestions(Long courseId, Integer count) {

        List<Question> rndQuestions = new ArrayList<>();
        final var questionList = questionRepository
                .findByCourse_IdAndActiveTrue(courseId);

        Random random = new Random();
        int minimum = 1;
        int maximum = questionList.size();

        if (maximum <= count)
            return questionList;

        else {

            for (int i = 0; i < count; i++) {
                int rndNum = random.nextInt(maximum - minimum + 1) + minimum;
                rndQuestions.add(questionList.get(rndNum));
            }
        }
        return rndQuestions;
    }
}