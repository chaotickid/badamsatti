package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.ForgotPassword;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.services.EmailService;
import com.bezkoder.springjwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * @author Aditya Patil
 * @Date 13-03-2023
 */

@RestController
@RequestMapping("/api/v1")
public class ForgotPasswordController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @PostMapping("/forgotpassword")
    public void forGotPasswordRequest(@RequestBody ForgotPassword forgotPassword) throws MessagingException {
        emailService.verificationSendMail(forgotPassword.getMail());
    }

    @PostMapping("/resetpassword")
    public User resetPasswordRequest(@RequestParam int id, @RequestBody ForgotPassword forgotPassword){
        return userService.updatePassword(id, forgotPassword);
    }
}