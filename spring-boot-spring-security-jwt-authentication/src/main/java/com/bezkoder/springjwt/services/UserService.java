package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.ForgotPassword;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Aditya Patil
 * @Date 13-03-2023
 */

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User updatePassword(int id, ForgotPassword forgotPassword){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        if(user.getForgotPasswordCode() != forgotPassword.getVerificationCode()){
            throw new RuntimeException("Invalid verification id");
        }
        user.setPassword(passwordEncoder.encode(forgotPassword.getNewPassword()));
        return user;
    }

}
