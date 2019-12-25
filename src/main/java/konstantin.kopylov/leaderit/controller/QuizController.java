package konstantin.kopylov.leaderit.controller;

import konstantin.kopylov.leaderit.entities.Quiz;
import konstantin.kopylov.leaderit.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping
    public Iterable<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("/{id}")
    public Quiz getById(@PathVariable("id") long id) {
        Optional<Quiz> searchResult = quizRepository.findById(id);
        if (!searchResult.isPresent()) {
            return null;
        }
        return searchResult.get();
    }

    @PostMapping
    public @ResponseBody
    String createNewQuiz(@RequestParam String name, @RequestParam String startDate,
                         @RequestParam String endDate, @RequestParam String activity) {

        Quiz quiz = new Quiz();
        quiz.setName(name);
        try {
            quiz.setStartDate(Date.valueOf(startDate));
            quiz.setEndDate(Date.valueOf(endDate));
        } catch (IllegalArgumentException e) {
            return "date is incorrect";
        }
        if (activity.equals("true")) {
            quiz.setActive(true);
        }

        quizRepository.save(quiz);

        return "success";
    }

    @PutMapping
    public @ResponseBody
    String updateQuiz(@RequestParam long id,
                      @RequestParam(required = false) String name,
                      @RequestParam(required = false) String startDate,
                      @RequestParam(required = false) String endDate,
                      @RequestParam(required = false) String activity) {
        Optional<Quiz> searchResult = quizRepository.findById(id);
        if (!searchResult.isPresent()) {
            return "not found";
        }
        Quiz quiz = searchResult.get();

        if (name != null) {
            quiz.setName(name);
        }
        if (startDate != null) {
            Date date;
            try {
                date = Date.valueOf(startDate);
            } catch (IllegalArgumentException e) {
                return "date is incorrect";
            }
            quiz.setStartDate(date);
        }
        if (endDate != null) {
            Date date;
            try {
                date = Date.valueOf(endDate);
            } catch (IllegalArgumentException e) {
                return "date is incorrect";
            }
            quiz.setStartDate(date);
        }
        if (activity != null) {
            if (activity.toLowerCase().equals("true")) {
                quiz.setActive(true);
            } else if (activity.toLowerCase().equals("false")) {
                quiz.setActive(false);
            }
        }

        quizRepository.save(quiz);

        return "success";
    }

    @DeleteMapping
    public @ResponseBody
    String deleteQuiz(@RequestParam long id) {
        Optional<Quiz> searchResult = quizRepository.findById(id);
        if (searchResult.isPresent()) {
            quizRepository.deleteById(id);
        } else {
            return "not found";
        }
        return "success";
    }
}
