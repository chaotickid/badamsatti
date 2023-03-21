package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.UserCardDetailsDto;
import com.bezkoder.springjwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aditya Patil
 * @Date 16-03-2023
 */

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserDetails")
    public UserCardDetailsDto getDetails(
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "lobbyId") int lobbyId
    ){
        return userService.getUserCardDetails(userId, lobbyId);
    }

    @GetMapping("/getPoints")
    public void getPoints(int joinCode){

    }
}
