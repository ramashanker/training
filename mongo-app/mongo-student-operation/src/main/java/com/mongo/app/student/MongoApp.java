package com.mongo.app.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
