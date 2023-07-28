package com.xam.quizservice.Service;


import com.xam.quizservice.DAO.QuizDao;
import com.xam.quizservice.Models.QuestionWrapper;
import com.xam.quizservice.Models.Quiz;
import com.xam.quizservice.Models.Response;
import com.xam.quizservice.feign.QuestionInterface;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

//    @Autowired
//    QuestionDao questionDao;

    @Autowired
    QuestionInterface questionInterface;

    @Autowired
    WebClient webClient;

    @Autowired
    CommonService commonService;

    public ResponseEntity<String> creatQuiz(String category, int numQ, String quizTitle) {
        // call generate url RestTemplate http://localhost:8080/question/generate
//        List<Integer> questions = generateQuestionsForQuiz(category, numQ);
        List<Integer> questions = generateQuestionsForQuizUsingFeign(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public List<Integer> generateQuestionsForQuizUsingFeign(String category, int numQ) {
        return questionInterface.generateQuestionsForQuiz(category,numQ).getBody();
    }

    public List<Integer> generateQuestionsForQuiz(String category, int numQ) {
         Mono<Integer[]> mono = webClient.get().uri("/generate?category="+category+"&numQ="+numQ)
                .retrieve().bodyToMono(Integer[].class);
        return Arrays.stream(mono.block()).toList();
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
//        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questions = commonService.getQuestionsFromIds(quiz.get().getQuestionIds());
//        List<QuestionWrapper> questionsForUser = new ArrayList<>();
//        for(Question q : questionsFromDB) {
//            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
//            questionsForUser.add(questionWrapper);
//        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

//    public List<QuestionWrapper> getQuestionsFromIds(List<Integer> questionIds) {
//        return questionInterface.getQuestionsFromIds(questionIds).getBody();
//    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
//        Quiz quiz = quizDao.findById(id).get();
//        List<Question> questions = quiz.getQuestions();
        Integer count = 0;
//        for(Response response: responses) {
//            if(response.getResponse().equals(questions.stream().filter(question -> question.getId().equals(response.getId())).findAny().get().getRightAnswer()))
//                count++;
//        }
        count = questionInterface.getScore(responses).getBody();
        return  new ResponseEntity<>(count, HttpStatus.OK);
    }
}
