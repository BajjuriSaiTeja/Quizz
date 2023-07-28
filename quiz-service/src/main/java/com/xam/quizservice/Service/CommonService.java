package com.xam.quizservice.Service;

import com.xam.quizservice.Models.QuestionWrapper;
import com.xam.quizservice.feign.QuestionInterface;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);
    long count = 0;

    @Autowired
    QuestionInterface questionInterface;

    @CircuitBreaker(name = "questionService", fallbackMethod = "fallbackGetQuestionsFromIds")
    public List<QuestionWrapper> getQuestionsFromIds(List<Integer> questionIds) {
        logger.info("count = " + count);
        count++;
        return questionInterface.getQuestionsFromIds(questionIds).getBody();
    }

    public List<QuestionWrapper> fallbackGetQuestionsFromIds(List<Integer> questionIds, Throwable th) {
        logger.error("Error = " + th);
        return Collections.emptyList();
    }

}
