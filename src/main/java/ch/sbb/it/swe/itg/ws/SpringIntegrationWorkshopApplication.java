package ch.sbb.it.swe.itg.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SpringIntegrationWorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationWorkshopApplication.class, args);
	}

}
