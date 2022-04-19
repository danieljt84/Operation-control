package com;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		 //consumerController.routine();
	}
	
=======
=======
>>>>>>> parent of 823efec (teste)
=======
>>>>>>> parent of 823efec (teste)
		 consumerController.routine();
	}
>>>>>>> parent of 823efec (teste)
}