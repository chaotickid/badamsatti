package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.models.User1;
import com.bezkoder.springjwt.models.User1Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Aditya Patil
 * @Date 08-03-2023
 */
@Controller
public class UserTestController {
    @MessageMapping("/user")
    @SendTo("/topic/user")
    public User1Response getUser(User1 user) {

        return new User1Response(user.getName());
    }
}
