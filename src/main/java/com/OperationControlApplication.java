package com;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.controller.ConsumerController;
import com.controller.RoutineController;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class OperationControlApplication {
	static ConfigurableApplicationContext context;
	static RoutineController routineController;

	public static void main(String[] args) {
		context = new SpringApplicationBuilder(OperationControlApplication.class).headless(false).run(args);
		routineController = context.getBean(RoutineController.class);
		try {
			routineController.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	ModelMapper ModelMapper() {
		return new ModelMapper();
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
