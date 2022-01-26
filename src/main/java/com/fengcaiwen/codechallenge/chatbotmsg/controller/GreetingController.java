package com.fengcaiwen.codechallenge.chatbotmsg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Welcome";
    }
}
