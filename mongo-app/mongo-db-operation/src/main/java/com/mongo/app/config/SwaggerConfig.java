package com.mongo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                                                      .select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.mongo.app"))
                                                      .paths(PathSelectors.regex("(?!/error).+"))
                                                      .paths(PathSelectors.regex("(?!/actuator).+"))
                                                      .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Mongo DB Operation")
                                   .description("Mongo DB CRUD Api")
                                   .version("1.0")
                                   .build();
    }
}
