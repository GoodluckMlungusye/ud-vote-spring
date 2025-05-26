package com.goodamcodes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/public")
public class WelcomeController {
    @GetMapping
    public String sayHello() {
        return "Welcome to the Spring Boot Security Demo Application!" +
                " This application serves as a practical guide to implementing security in Spring Boot projects using Spring Security." +
                " It showcases how to configure authentication and authorization, manage user roles, secure REST endpoints," +
                " and apply method-level restrictions. Whether you're using in-memory authentication, integrating with databases," +
                " this demo provides a solid foundation for building secure Spring-based applications using JWT.";
    }
}
