package com.hello.community.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",required = false)String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
