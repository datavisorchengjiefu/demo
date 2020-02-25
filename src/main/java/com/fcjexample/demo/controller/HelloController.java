package com.fcjexample.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Object helloha() {
        return "hello haha fcjdormi";
    }
}
