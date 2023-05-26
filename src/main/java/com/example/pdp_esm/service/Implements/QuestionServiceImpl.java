package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.QuestionDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.Answer;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.AnswerRepository;
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
    private final AnswerRepository answerRepository;
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
        final var byId = questionRepository.findById(question_id);
        if (byId.isPresent()) {
            byId.get().setActive(false);
            return new ApiResponse<>("Question is Terminated", true);
        } else
            return new ApiResponse<>("Question not found", false);
    }

    public Question settingValues(Question question, QuestionDTO questionDTO) {

        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findById(questionDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", questionDTO.getCourseId())));

        Optional<Answer> optionalAnswer = Optional.ofNullable(answerRepository.findById(questionDTO.getAnswerId())
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", questionDTO.getCourseId())));

        question.setCourse(optionalCourse.get());
        question.setQuestionType(QuestionType.valueOf(questionDTO.getQuestionType()));
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(optionalAnswer.get());
        question.setActive(true);

        return questionRepository.save(question);
    }
}