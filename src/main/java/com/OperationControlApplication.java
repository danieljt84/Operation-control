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

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class OperationControlApplication {
	static ConfigurableApplicationContext context;
	static ConsumerController consumerController;
	public static void main(String[] args) {
		 context = new SpringApplicationBuilder(OperationControlApplication.class)
				.headless(false).run(args);
		 consumerController = context.getBean(ConsumerController.class);
		 //consumerController.routine();
		 //consumerController.createExcel();
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
