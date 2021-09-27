package com.zacharyhuang.accountValidation;

import com.zacharyhuang.accountValidation.config.MDCTaskDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class AccountValidationApplication {

	@Bean
	public Executor externalProviderAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(8);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(20);
		executor.setTaskDecorator(new MDCTaskDecorator());
		executor.initialize();
		return executor;
	}
	public static void main(String[] args) {
		SpringApplication.run(AccountValidationApplication.class, args);
	}

}
