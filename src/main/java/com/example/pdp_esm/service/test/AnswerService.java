package com.example.pdp_esm.service.test;

import java.util.Date;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pdp_esm.dto.test.AnswerDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.test.AnswerTest;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.test.AnswerRepositoryTest;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepositoryTest answerRepository;
    private final QuestionRepository questionRepository;

    public ApiResponse<?> createAnswer(AnswerDTO answerDTO) {

        AnswerTest answer = new AnswerTest();
        AnswerTest save = settingValues(answer, answerDTO);
        save.setCreatedAt(new Date());
        answerRepository.save(save);

        return ApiResponse.builder()
                .message("AnswerTest Created")
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
        AnswerTest answer = optionalAnswer.get();
        return ApiResponse.builder()
                .message("Answers by " + id + " id! ")
                .success(true)
                .data(answer) //toResAnswer
                .build();
    }

    public ApiResponse<?> updateAnswer(Long id, AnswerDTO answerDTO) {

        Optional<AnswerTest> optionalAnswer = answerRepository.findById(id);
        AnswerTest answer = optionalAnswer.get();

        AnswerTest save = settingValues(answer, answerDTO);
        save.setUpdatedAt(new Date());
        answerRepository.save(save);
        return ApiResponse.builder()
                .message("AnswerTest Updated!")
                .success(true)
                .data(save)
                .build();
    }

    public ApiResponse<?> deleteAnswer(Long id) {

        final var byId = answerRepository.findById(id);
        byId.ifPresent(answerRepository::delete);
        return new ApiResponse<>("AnswerTest Deleted!", true);
    }

    public AnswerTest settingValues(AnswerTest answer, AnswerDTO answerDTO) {

        final var questionId = answerDTO.getQuestion_id();
        final var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "question_id", questionId));

        answer.setBody(answerDTO.getInput());
        answer.setQuestion(question);
        answer.setPosition(answerDTO.getPosition());
        answer.setStatus(answerDTO.getStatus());
        return answer;
    }
}