package com.example.pdp_esm.service.test;

import java.util.Optional;

import com.example.pdp_esm.entity.test.Answer;
import com.example.pdp_esm.dto.test.AnswerDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.repository.test.AnswerRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public ApiResponse<?> createAnswer(AnswerDTO answerDTO) {

        Answer answer = new Answer();
        Answer save = settingValues(answer, answerDTO);

        return ApiResponse.builder()
                .message("Answer Created")
                .success(true)
                .data(save) //toResAnswer
                .build();
    }

    public ApiResponse<?> getAllAnswers() {
        final var answerList = answerRepository.findAll();
        return ApiResponse.builder()
                .message("Answers List")
                .success(true)
                .data(answerList) //toResAnswerList
                .build();
    }

    public ApiResponse<?> getOneAnswer(Long id) {
        final var optionalAnswer = answerRepository.findById(id);
        Answer answer = optionalAnswer.get();
        return ApiResponse.builder()
                .message("Answers by " + id + " id! ")
                .success(true)
                .data(answer) //toResAnswer
                .build();
    }

    public ApiResponse<?> updateAnswer(Long id, AnswerDTO answerDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        Answer answer = optionalAnswer.get();

        final var save = settingValues(answer, answerDTO);
        return ApiResponse.builder()
                .message("Answer Updated!")
                .success(true)
                .data(save)
                .build();
    }

    public ApiResponse<?> deleteAnswer(Long id) {

        final var byId = answerRepository.findById(id);
        byId.ifPresent(answerRepository::delete);
        return new ApiResponse<>("Answer Deleted!", true);
    }

    public Answer settingValues(Answer answer, AnswerDTO answerDTO) {

        final var questionId = answerDTO.getQuestion_id();
        final var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "question_id", questionId));

        answer.setBody(answerDTO.getInput());
        answer.setQuestion(question);
        answer.setPosition(answerDTO.getPosition());
        answer.setStatus(answerDTO.isStatus());
        return answer;
    }
}