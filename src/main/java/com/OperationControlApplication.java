package com;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import com.controller.RoutineController;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class OperationControlApplication {
	static ConfigurableApplicationContext context;
	static RoutineController routineController;

	public static final Logger LOG = LoggerFactory.getLogger(OperationControlApplication.class);

	public static void main(String[] args) {
		context = new SpringApplicationBuilder(OperationControlApplication.class).headless(false).run(args);
		routineController = context.getBean(RoutineController.class);
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
