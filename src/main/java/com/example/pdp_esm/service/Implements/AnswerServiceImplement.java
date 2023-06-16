//package com.example.pdp_esm.service.Implements;
//
//import lombok.RequiredArgsConstructor;
//import com.example.pdp_esm.entity.Answer;
//import com.example.pdp_esm.dto.AnswerDTO;
//import org.springframework.stereotype.Service;
//import com.example.pdp_esm.service.AnswerService;
//import com.example.pdp_esm.dto.result.ApiResponse;
//import com.example.pdp_esm.repository.AnswerRepository;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AnswerServiceImplement implements AnswerService {
//
//    private final AnswerRepository answerRepository;
//
//    @Override
//    public ApiResponse<?> createAnswer(AnswerDTO answerDTO) {
//
//        Answer answer = new Answer();
//        Answer save = settingValues(answer, answerDTO);
//
//        return ApiResponse.builder()
//                .message("Answer Created")
//                .success(true)
//                .data(save) //toResAnswer
//                .build();
//    }
//
//    @Override
//    public ApiResponse<?> getAllAnswers() {
//        final var answerList = answerRepository.findAll();
//        return ApiResponse.builder()
//                .message("Answers List")
//                .success(true)
//                .data(answerList) //toResAnswerList
//                .build();
//    }
//
//    @Override
//    public ApiResponse<?> getOneAnswer(Long id) {
//        final var optionalAnswer = answerRepository.findById(id);
//        Answer answer = optionalAnswer.get();
//        return ApiResponse.builder()
//                .message("Answers by " + id + " id! ")
//                .success(true)
//                .data(answer) //toResAnswer
//                .build();
//    }
//
//    @Override
//    public ApiResponse<?> updateAnswer(Long id, AnswerDTO answerDTO) {
//        Optional<Answer> optionalAnswer = answerRepository.findById(id);
//        Answer answer = optionalAnswer.get();
//
//        final var save = settingValues(answer, answerDTO);
//        return ApiResponse.builder()
//                .message("Answer Updated!")
//                .success(true)
//                .data(save)
//                .build();
//    }
//
//    @Override
//    public ApiResponse<?> deleteAnswer(Long id) {
//
//        final var byId = answerRepository.findById(id);
//        byId.ifPresent(answerRepository::delete);
//        return new ApiResponse<>("Answer Deleted!", true);
//    }
//
//    public Answer settingValues(Answer answer, AnswerDTO answerDTO) {
//
////        answer.setActive(true);
////        answer.setTrue_answer(answerDTO.getTrue_answer());
////        answer.setAnswer1(answerDTO.getAnswer1() == null ? "null": answerDTO.getAnswer1());
////        answer.setAnswer2(answerDTO.getAnswer2() == null ? "null": answerDTO.getAnswer2());
////        answer.setAnswer3(answerDTO.getAnswer3() == null ? "null": answerDTO.getAnswer3());
////        answer.setAnswer4(answerDTO.getAnswer4() == null ? "null": answerDTO.getAnswer4());
//        return answerRepository.save(answer);
//    }
//}