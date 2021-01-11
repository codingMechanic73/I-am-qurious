package com.example.qurious.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String greeting() {
        return "redirect:/swagger-ui/index.html";
    }

}
