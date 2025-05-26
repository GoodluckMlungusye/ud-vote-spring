package com.goodamcodes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
public class ResourceTestController {

    @GetMapping
    public String getUserResource() {
        return "About";
    }
}

