package ru.kostromin.erprecivellorecipes;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kostromin.erprecivellorecipes.service.ScheduledProcess;

@SpringBootApplication
public class ErpReciveLloRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpReciveLloRecipesApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(List<ScheduledProcess> services) {
		return args -> services.forEach(ScheduledProcess::run);
	}
}
