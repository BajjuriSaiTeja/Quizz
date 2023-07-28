package com.xam.questionservice.controller;


import com.xam.questionservice.Models.Question;
import com.xam.questionservice.Models.QuestionWrapper;
import com.xam.questionservice.Models.Response;
import com.xam.questionservice.Service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("question")
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{topic}")
    public List<Question> getQuestionsByCategory(@PathVariable("topic") String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public String addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(
            @RequestParam String category,
            @RequestParam Integer numQ
    ) {
        Map<String, Integer> test = new HashMap<String, Integer>();
        test.getOrDefault("c", 0);
        return questionService.getQuestionsForQuiz(category, numQ);
    }

    // getQuestionById
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionIds) {
        logger.info("getting questions for ids" + questionIds.toString());
        return questionService.getQuestionsFromIds(questionIds);
    }

    // calculate
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.calculateScore(responses);
    }
}
