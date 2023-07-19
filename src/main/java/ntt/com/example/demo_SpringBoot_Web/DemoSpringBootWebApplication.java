package ntt.com.example.demo_SpringBoot_Web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DemoSpringBootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootWebApplication.class, args);
	}

}
