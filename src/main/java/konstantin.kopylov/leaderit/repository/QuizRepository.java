package konstantin.kopylov.leaderit.repository;

import konstantin.kopylov.leaderit.entities.Quiz;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface QuizRepository extends CrudRepository <Quiz, Long> {
    List<Quiz> findByName(String name);
    List<Quiz> findByStartDate(Date date);
    List<Quiz> findByEndDate(Date date);
    List<Quiz> findByActive(Boolean active);
}
