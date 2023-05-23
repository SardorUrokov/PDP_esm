package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.QuestionDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponse<?> createQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        Question save = settingValues(question, questionDTO);

        return ApiResponse.builder()
                .message("Question Saved!")
                .success(true)
                .data(save)
                .build();
    }

    @Override
    public ApiResponse<?> getAllQuestions() {
        List<Question> questionList = questionRepository.findAllByActiveTrue();

        return ApiResponse.builder()
                .message("All Active True Questions List")
                .success(true)
                .data(questionList)
                .build();
    }

    @Override
    public ApiResponse<?> getAllByActive(String active) {

        if (!(active.equals("true") || active.equals("false")))
            return new ApiResponse<>("Active type doesn't exist " + active, false);

        List<Question> allQuestionsByActive = questionRepository.findAllByActive(Boolean.valueOf(active));
        return new ApiResponse<>("All Questions By Active " + active, true, allQuestionsByActive);

    }

    @Override
    public ApiResponse<?> getOneQuestion(Long question_id) {
        Optional<Question> optionalQuestion = Optional.ofNullable(questionRepository.findById(question_id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", question_id)));

        return ApiResponse.builder()
                .message("Question with " + question_id + " id")
                .success(true)
                .data(optionalQuestion)
                .build();
    }

    @Override
    public ApiResponse<?> updateQuestion(Long question_id, QuestionDTO questionDTO) {
        Optional<Question> optionalQuestion = questionRepository.findById(question_id);
        Question question = optionalQuestion.get();
        Question save = settingValues(question, questionDTO);

        return ApiResponse.builder()
                .message("Question Updated!")
                .success(true)
                .data(save)
                .build();
    }

    @Override
    public ApiResponse<?> deleteQuestion(Long question_id) {

        if (questionRepository.findById(question_id).isPresent()) {
            questionRepository.findById(question_id).get().setActive(false);
            return new ApiResponse<>("Question is Terminated", true);
        } else
            return new ApiResponse<>("Question not found", false);
    }

    public Question settingValues(Question question, QuestionDTO questionDTO) {

        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findById(questionDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", questionDTO.getCourseId())));
        Course course = optionalCourse.get();

        question.setCourse(course);
        question.setQuestion(questionDTO.getQuestion());
        question.setTrue_answer(questionDTO.getTrue_answer());
        question.setWrong_answer1(questionDTO.getWrong_answer1());
        question.setWrong_answer2(questionDTO.getWrong_answer2());
        question.setWrong_answer3(questionDTO.getWrong_answer3());
        return questionRepository.save(question);
    }
}