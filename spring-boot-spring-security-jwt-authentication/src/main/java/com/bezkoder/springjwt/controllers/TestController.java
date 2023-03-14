package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aditya Patil
 * @Date 14-03-2023
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/whoisrk")
    public String get(){
        return "Chutya yedazava RK";
    }
}
