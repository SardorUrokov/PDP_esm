package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.entity.ExamineInfo;
import com.example.pdp_esm.entity.enums.ExamType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.pdp_esm.repository.*;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.dto.AnswerObject;
import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.dto.CheckingAttemptsDTO;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.dto.result.ResExamResults;
import com.example.pdp_esm.repository.test.AnswerRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExaminingService {

    private final AttemptsService attemptsService;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final QuestionServiceImpl questionService;
    private final QuestionRepository questionRepository;
    private final ExamResultServiceImpl examResultService;
    private final ExamResultRepository examResultRepository;
    private final CertificateServiceImpl certificateService;
    private final ExamineInfoRepository examineInfoRepository;
    private final ReserveUsersRepository reserveUsersRepository;

    public ApiResponse<?> checkingAnswers(CheckingAttemptsDTO checkingAttemptsDTO) {

        final var studentId = checkingAttemptsDTO.getStudent_id();
        final var existsByStudentId = examResultRepository.existsByStudentId(studentId);

        if (existsByStudentId)
            return new ApiResponse<>(
                    "Exam Result with this " + studentId + " student_id is Already saved! ",
                    false);
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

            final var student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));

            LocalDateTime localDateTime = LocalDateTime.now();
            Date now = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            final var studentGroup = student.getGroup();
            final ExamineInfo examineInfo = examineInfoRepository.findByGroupsIn(Collections.singleton(studentGroup))
                    .orElseThrow(() -> new ResourceNotFoundException("Examine Info", "student_group_id", studentGroup.getId()));

            attemptsService.updateAttempts(studentId, examineInfo.getId());
            ExamResultDTO examResultDTO = new ExamResultDTO();

            if (now.after(examineInfo.getStartsDate())) {

                examResultDTO.setScore(score);
                examResultDTO.setStudentId(studentId);
                examResultDTO.setExamineInfoId(examineInfo.getId());  //need to set examine_info_id
                examResultDTO.setQuestionsIdList(questionsIdsList);

            } else return
                    new ApiResponse<>("The Examine time has not started yet", false);

            if (score >= 60) {
                examResultDTO.setResultType(ResultType.SUCCESS);
                message = "Student SUCCEED!";
            } else
                examResultDTO.setResultType(ResultType.FAIL);

            ApiResponse<?> examResult = examResultService.createExamResult(examResultDTO);
            ResExamResults data = (ResExamResults) examResult.getData();

            ResExamResults resExamResults = new ResExamResults(
                    data.getScore(),
//                    data.getResExamineInfoDTO(),
                    data.getStudentInfo(),
                    data.getResultType(),
                    data.getSubmitted_time(),
                    data.getQuestionList()
            );

            if (examineInfo.getExamType().equals(ExamType.TOTAL_EXAM) &&  data.getResultType().equals("SUCCESS"))
                certificateService.createCertificate(studentId);

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
            final var studentId = student.getId();

            final var examineInfo = examineInfoRepository.findByGroupsIn(Collections.singleton(studentGroup))
                    .orElseThrow(() -> new ResourceNotFoundException("Examine Info", "group", studentGroup.getId()));

            final boolean studentAttempts = attemptsService.checkStudentAttempts(studentId, examineInfo.getModule().getId());

            if (studentAttempts) {

                final var numOfQuestions = examineInfo.getNumOfQuestions();
                final var courseId = studentGroup.getCourse().getId();
                questionList = rndQuestions(courseId, numOfQuestions); // it returns the number of questions set in numOfQuestions field

            } else
                return
                        new ApiResponse<>("The Student has used all attempts", false);
        } else {
            return
                    new ApiResponse<>("User is not a Student", false);
        }
        final var resQuestionDTOList = questionService.toResQuestionDTOList(questionList);

        return ApiResponse.builder()
                .message("Exam Questions!")
                .success(true)
                .data(resQuestionDTOList)
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