package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Properties;

/**
 * @author Aditya Patil
 * @Date 13-03-2023
 */
@Service
@Transactional
@Slf4j
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    CardServices cardServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static final String BADAMSATTI_HOST_MAIL = "playbadamsatti@gmail.com";

    public void verificationSendMail(String to) throws MessagingException {
        User liftedUser = userRepository.findByEmail(to);
        if(liftedUser == null){
            throw new RuntimeException("No user found");
        }
        int code = cardServices.generate6DigitCode();
        liftedUser.setForgotPasswordCode(code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(BADAMSATTI_HOST_MAIL);
        message.setTo(to);
        message.setText("" +
                "Hi user,\n\n" +
                "We received a request to access your Badamsatti Account " + to + "\nthrough your email address. Your Badamsatti account recovery code is: \n" +
                code + "\n" +
                "If you did not request this code, it is possible that someone else is trying to access the Badamsatti Account\n" +
                "Do not forward or give this code to anyone.\n\n" +
                "Sincerely yours,\n\n" +
                "The Badamsatti Accounts team.\n\n\n\n" +
                "Enjoy Badamsatti !"

        );
        message.setSubject("Account recovery");
        javaMailSender.send(message);
        System.out.println("Message send successfully");
        log.debug("Message send successfully");

    }
}
