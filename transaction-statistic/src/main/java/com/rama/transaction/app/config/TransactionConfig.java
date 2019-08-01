package com.rama.transaction.app.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.rama.transaction.app.util.DataConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class TransactionConfig {

	@Bean
	public Docket newApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Transaction data processing")
				.description("Rest Api for transaction and Statistics data process").version("1.0").build();

	}

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(DataConstants.CORE_THREAD);
		executor.setMaxPoolSize(DataConstants.MAX_THREAD);
		executor.setThreadNamePrefix("clean_up_task_executor_thread");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
	    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
	    builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
	    return builder;
	}
}
