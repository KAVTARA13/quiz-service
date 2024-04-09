package com.example.quizservice.sevice;

import com.example.quizservice.feign.QuizInterface;
import com.example.quizservice.model.QuestionWrapper;
import com.example.quizservice.model.Quiz;
import com.example.quizservice.model.Response;
import com.example.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizInterface quizInterface;

    public QuizService(QuizRepository quizRepository, QuizInterface quizInterface) {
        this.quizRepository = quizRepository;
        this.quizInterface = quizInterface;
    }

    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {
        List<Integer> questionList = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionList);
        return new ResponseEntity<>(quizRepository.save(quiz), HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<QuestionWrapper> questionList = quizInterface.getQuestionsFromId(quiz.get().getQuestionIds()).getBody();

        return new ResponseEntity<>(questionList,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
        Integer right = quizInterface.getScore(response).getBody();
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
