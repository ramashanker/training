package com.rama.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
   /* @RequestMapping("/")
    public String getMessage() {
        return "Hello from controller";
    }*/

    @GetMapping("/")
    public String getMessage() {
        return "Hello from controller";
    }
}
