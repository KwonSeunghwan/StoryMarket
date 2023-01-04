package org.zerock.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StorymarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorymarketApplication.class, args);
	}

}
