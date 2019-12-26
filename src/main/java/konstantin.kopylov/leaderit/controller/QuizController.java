package konstantin.kopylov.leaderit.controller;

import konstantin.kopylov.leaderit.entities.Quiz;
import konstantin.kopylov.leaderit.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping
    public ResponseEntity<Iterable<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok().body(quizRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable("id") long id) {
        Optional<Quiz> searchResult = quizRepository.findById(id);
        return searchResult.map(quiz -> ResponseEntity.ok().body(quiz)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Quiz> createNewQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok().body(quizRepository.save(quiz));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable("id") long id, @RequestBody Quiz quiz) {
        Optional<Quiz> searchResult = quizRepository.findById(id);

        return searchResult.map(foundQuiz -> {
            System.out.println("found");
            foundQuiz.setName(quiz.getName());
            foundQuiz.setStartDate(quiz.getStartDate());
            foundQuiz.setEndDate(quiz.getEndDate());
            foundQuiz.setActive(quiz.isActive());
            foundQuiz.setQuestions(quiz.getQuestions());
            return ResponseEntity.ok().body(quizRepository.save(foundQuiz));
        }).orElseGet(() -> ResponseEntity.ok().body(quizRepository.save(quiz)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable("id") long id) {
        Optional<Quiz> searchResult = quizRepository.findById(id);
        return searchResult.map(quiz -> {
            quizRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
