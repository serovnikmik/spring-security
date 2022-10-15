package com.example.spring.security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(){
        return "secured part of the web site";
    }

}
