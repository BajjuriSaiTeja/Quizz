package com.xam.quizservice.feign;

import com.xam.quizservice.Models.QuestionWrapper;
import com.xam.quizservice.Models.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// @FeignClient("question-service") for routing through eureka
@FeignClient("api-gateway")
public interface QuestionInterface {

    // generate
    @GetMapping("/question-service/question/generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(
            @RequestParam String category,
            @RequestParam Integer numQ
    );

    // getQuestionById
    @PostMapping("/question-service/question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionIds);

    // calculate
    @PostMapping("/question-service/question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
