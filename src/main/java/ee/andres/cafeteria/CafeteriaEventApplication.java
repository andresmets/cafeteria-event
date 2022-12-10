package ee.andres.cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CafeteriaEventApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CafeteriaEventApplication.class, args);
	}

}
