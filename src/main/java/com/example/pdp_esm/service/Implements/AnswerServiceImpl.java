package com.example.pdp_esm.service.Implements;

import java.util.Date;
import java.util.List;

import com.example.pdp_esm.entity.enums.QuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.pdp_esm.dto.AnswerDTO;
import com.example.pdp_esm.entity.Answer;
import com.example.pdp_esm.service.AnswerService;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.repository.QuestionRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.test.AnswerRepository;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public ApiResponse<?> createAnswer(AnswerDTO answerDTO) {

        final var exists = answerRepository.existsByQuestion_IdAndPositionAndStatus(
                answerDTO.getQuestion_id(), answerDTO.getPosition(), answerDTO.getStatus()
        );

        if (exists)
            return
                    new ApiResponse<>("Such a answer is already saved!", false);
        else {

            Answer answer = new Answer();
            Answer save = settingValues(answer, answerDTO);
            answerRepository.save(save);

            return ApiResponse.builder()
                    .message("Answer Created")
                    .success(true)
                    .data(save) //toResAnswer
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllAnswers() {
        final var answerList = answerRepository.findAll();
        return ApiResponse.builder()
                .message("Answers List")
                .success(true)
                .data(answerList) //toResAnswerList
                .build();
    }

    @Override
    public ApiResponse<?> getOneAnswer(Long id) {
        final var answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "answer_id", id));

        return ApiResponse.builder()
                .message("Answers by " + id + " id! ")
                .success(true)
                .data(answer) //toResAnswer
                .build();
    }

    @Override
    public ApiResponse<?> updateAnswer(Long id, AnswerDTO answerDTO) {

        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "answer_id", id));

        Answer save = settingValues(answer, answerDTO);
        save.setUpdatedAt(new Date());
        answerRepository.save(save);
        return ApiResponse.builder()
                .message("Answer Updated!")
                .success(true)
                .data(save)
                .build();
    }

    @Override
    public ApiResponse<?> deleteAnswer(Long id) {

        final var answerTest = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "answer_id", id));
        answerRepository.delete(answerTest);

        return new ApiResponse<>("Answer Deleted!", true);
    }

    public Answer settingValues(Answer answer, AnswerDTO answerDTO) {

        final var questionId = answerDTO.getQuestion_id();
        final var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "question_id", questionId));

        answer.setBody(answerDTO.getInput());
        answer.setQuestion(question);
        answer.setPosition(answerDTO.getPosition());
        answer.setStatus(answerDTO.getStatus());
        return answer;
    }

    public AnswerDTO toAnswerDTO(Answer answerTest) {
        return AnswerDTO.builder()
                .input(answerTest.getBody())
                .position(answerTest.getPosition())
                .question_id(answerTest.getQuestion().getId())
                .status(answerTest.isStatus())
                .build();
    }

    public AnswerDTO toExamQuestionsAnswer(Answer answer){

        final var questionType = answer.getQuestion().getQuestionType();

        if (!(questionType.equals(QuestionType.WRITE_MISSING_WORD)) && !(questionType.equals(QuestionType.TRUE_FALSE))){
            return AnswerDTO.builder()
                    .input(answer.getBody())
//                    .position(answer.getPosition())
                    .question_id(answer.getQuestion().getId())
//                    .status(answer.isStatus())
                    .build();
        } else {
            return AnswerDTO.builder()
                    .question_id(answer.getQuestion().getId())
                    .input(answer.getBody())
                    .build();
        }
    }

    public List<AnswerDTO> toAnswerDTOList(List<Answer> answerTests) {
        return answerTests.stream().map(this::toAnswerDTO).toList();
    }

    public List<AnswerDTO> toExamQuestionsAnswerList(List<Answer> answerTests) {
        return answerTests.stream().map(this::toExamQuestionsAnswer).toList();
    }
}