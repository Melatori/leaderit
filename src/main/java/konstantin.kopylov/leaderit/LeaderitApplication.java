package konstantin.kopylov.leaderit;

import konstantin.kopylov.leaderit.controller.QuizController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = QuizController.class)
public class LeaderitApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderitApplication.class, args);
	}

}
