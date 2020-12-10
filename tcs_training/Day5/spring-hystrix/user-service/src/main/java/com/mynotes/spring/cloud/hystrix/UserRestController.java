package com.mynotes.spring.cloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserRestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/personalized/{id}")
    @HystrixCommand(fallbackMethod = "recommendationFallback")
    public Product[] personalized(@PathVariable int id) {
        Product[] result = restTemplate.getForObject("http://recommendation-service/recommendations", Product[].class);
        return result;
    }

    public Product[] recommendationFallback(int id) {
        System.out.println("=======recommendationFallback=========" + id);
        return new Product[0];
    }

}
