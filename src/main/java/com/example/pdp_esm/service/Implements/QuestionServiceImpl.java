package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.QuestionDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResQuestionDTO;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponse<?> createQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        Question save = settingValues(question, questionDTO);
        save.setCreatedAt(new Date());
        questionRepository.save(save);

        return ApiResponse.builder()
                .message("Question Saved!")
                .success(true)
                .data(toResQuestionDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> getAllQuestions() {
        List<Question> questionList = questionRepository.findAllByActiveTrue();

        return ApiResponse.builder()
                .message("All Active True Questions List")
                .success(true)
                .data(toResQuestionDTOList(questionList))
                .build();
    }

    @Override
    public ApiResponse<?> getAllByActive(String active) {

        if (!(active.equals("true") || active.equals("false")))
            return new ApiResponse<>("Active type doesn't exist " + active, false);

        List<Question> allQuestionsByActive = questionRepository.findAllByActive(Boolean.valueOf(active));
        return new ApiResponse<>("All Questions By Active " + active, true, toResQuestionDTOList(allQuestionsByActive));

    }

    @Override
    public ApiResponse<?> getOneQuestion(Long question_id) {
        Question question = questionRepository.findById(question_id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", question_id));

        return ApiResponse.builder()
                .message("Question with " + question_id + " id")
                .success(true)
                .data(toResQuestionDTO(question))
                .build();
    }

    @Override
    public ApiResponse<?> updateQuestion(Long question_id, QuestionDTO questionDTO) {

        Question question = questionRepository.findById(question_id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "question_id", question_id));
        Question save = settingValues(question, questionDTO);
        save.setUpdatedAt(new Date());
        questionRepository.save(save);

        return ApiResponse.builder()
                .message("Question Updated!")
                .success(true)
                .data(toResQuestionDTO(save))
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

        Course course = courseRepository.findById(questionDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", questionDTO.getCourseId()));

        question.setCourse(course);
        question.setQuestionType(QuestionType.valueOf(questionDTO.getQuestionType()));
        question.setQuestionText(questionDTO.getQuestionText());
        question.setActive(true);

        return question;
    }

    public ResQuestionDTO toResQuestionDTO(Question question) {
        return ResQuestionDTO.builder()
                .courseName(question.getCourse().getName())
                .questionType(String.valueOf(question.getQuestionType()))
                .question(question.getQuestionText())
                .build();
    }

    public List<ResQuestionDTO> toResQuestionDTOList(List<Question> questionList) {
        return questionList.stream().map(this::toResQuestionDTO).toList();
    }
}