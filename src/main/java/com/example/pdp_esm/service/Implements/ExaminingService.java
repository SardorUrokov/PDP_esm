package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.entity.ExamineInfo;
import com.example.pdp_esm.entity.User;
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
import com.example.pdp_esm.repository.AnswerRepository;
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
    private final UserRepository userRepository;

    public ApiResponse<?> checkingAnswers(CheckingAttemptsDTO checkingAttemptsDTO) {

        final var otp = checkingAttemptsDTO.getStudent_otp();
        final var reserveUser = reserveUsersRepository.findByOtpCode(otp)
                .orElseThrow(() -> new ResourceNotFoundException("User", "otp", otp));
        final var userPhoneNumber = reserveUser.getPhoneNumber();

        final var user = userRepository.findUserByPhoneNumber(userPhoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "phone_number", userPhoneNumber));

        final var usersDType = userRepository.getUsersDType(userPhoneNumber);

        Long studentId;
        if (usersDType.equals("Student"))
            studentId = user.getId();
        else
            return new ApiResponse<>("User Not a Student!", false);

        final var existsByStudentId = examResultRepository.existsByStudentId(studentId);

        final var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));

        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        final var studentGroup = student.getGroup();
        final ExamineInfo examineInfo = examineInfoRepository.findByGroupsIn(Collections.singleton(studentGroup))
                .orElseThrow(() -> new ResourceNotFoundException("Examine Info", "student_group_id", studentGroup.getId()));

        final var numOfQuestions = examineInfo.getNumOfQuestions();
        float scorePerAnswer = 100f / numOfQuestions;

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

                    if (answerBody.equalsIgnoreCase(answerDTOInput) && answerDTO.getStatus().equals(true)) {
                        score += scorePerAnswer;
                    }

                } else if (questionType.equals(String.valueOf(QuestionType.SEQUENCE))) {

                    final var answerTestList = answerRepository.findByQuestion_IdOrderByPosition(questionId);

                    int k = 1;
                    for (int j = 1; j < answerDTOList.size(); j++) {

                        if (answerDTOList.get(k).getInput().equalsIgnoreCase(answerTestList.get(j).getBody())) {
                            score += scorePerAnswer;
                        }
                    }
                } else if ((questionType.equals(String.valueOf(QuestionType.TEST)))
                        ||
                        questionType.equals(String.valueOf(QuestionType.TRUE_FALSE))) {

                    final var questionByStatusTrue = answerRepository.findByQuestion_IdAndStatusTrue(questionId)
                            .orElseThrow(() -> new ResourceNotFoundException("Status TRUE Question", "question_id", questionId));

                    if (questionByStatusTrue.getBody().equalsIgnoreCase(answerDTO.getInput())) {
                        score += scorePerAnswer;
                    }
                }
            }

            attemptsService.updateAttempts(studentId, examineInfo.getId());
            ExamResultDTO examResultDTO = new ExamResultDTO();

            if (now.after(examineInfo.getStartsDate())) {

                examResultDTO.setScore(score);
                examResultDTO.setStudentId(studentId);
                examResultDTO.setExamineInfoId(examineInfo.getId());
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
                    data.getExamResult_id(),
                    data.getScore(),
                    data.getStudentInfo(),
                    data.getResultType(),
                    data.getSubmitted_time(),
                    data.getQuestionList()
            );

            if (examineInfo.getExamType().equals(ExamType.TOTAL_EXAM) && data.getResultType().equals("SUCCESS"))
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