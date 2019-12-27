package konstantin.kopylov.leaderit.controller;

import konstantin.kopylov.leaderit.entities.Quiz;
import konstantin.kopylov.leaderit.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

    private static final int PAGE_SIZE = 100;

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping
    public ResponseEntity<Iterable<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok().body(quizRepository.findAll());
    }

    @GetMapping("/sorted")
    public ResponseEntity<Iterable<Quiz>> getFilteredQuizzes(@RequestParam String sortBy,
                                                             @RequestParam String sortOrder,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) Date startDate,
                                                             @RequestParam(required = false) Date endDate,
                                                             @RequestParam(required = false) Boolean active) {
        Iterable<Quiz> foundQuizzes;
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setStartDate(startDate);
        quiz.setEndDate(endDate);
        quiz.setActive(active);

        if (!(sortBy.equals("name") || sortBy.equals("startDate"))) {
            foundQuizzes = quizRepository.findAll(Example.of(quiz));
            return ResponseEntity.ok().body(foundQuizzes);
        }

        Pageable page;

        if (sortBy.equals("name")) {
            if (sortOrder.equals("DESC")) {
                page = PageRequest.of(0, PAGE_SIZE, Sort.by(Direction.DESC, "name"));
            } else {
                page = PageRequest.of(0, PAGE_SIZE, Sort.by(Direction.ASC, "name"));
            }
        } else {
            if (sortOrder.equals("DESC")) {
                page = PageRequest.of(0, PAGE_SIZE, Sort.by(Direction.DESC, "startDate"));
            } else {
                page = PageRequest.of(0, PAGE_SIZE, Sort.by(Direction.ASC, "startDate"));
            }
        }

        foundQuizzes = quizRepository.findAll(Example.of(quiz), page).getContent();

        return ResponseEntity.ok().body(foundQuizzes);
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
