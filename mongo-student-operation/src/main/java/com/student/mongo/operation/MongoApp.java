package com.student.mongo.operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
/*@EnableSwagger2*/
public class MongoApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MongoApp.class, args);
    }
    /*@Bean
    public Docket newApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(apiInfo())
    			.select()
    			.apis(RequestHandlerSelectors.any())
    			.paths(PathSelectors.regex("/mongo/.*"))
    			.build();
    }
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.title("Mongo data processing")
    			.description("Rest Api for mongo data process")
    			.version("1.0")
    			.build();
    			
    }*/
}
