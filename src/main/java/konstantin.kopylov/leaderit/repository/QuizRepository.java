package konstantin.kopylov.leaderit.repository;

import konstantin.kopylov.leaderit.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByName(String name);
    List<Quiz> findByStartDate(Date date);
    List<Quiz> findByEndDate(Date date);
    List<Quiz> findByActive(Boolean active);
    List<Quiz> findByNameAndStartDate(String name, Date date);
}
