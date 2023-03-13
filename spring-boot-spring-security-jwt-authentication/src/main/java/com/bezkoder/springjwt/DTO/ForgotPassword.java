package com.bezkoder.springjwt.DTO;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aditya Patil
 * @Date 13-03-2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPassword {
    private String mail;

    private int verificationCode;

    private String newPassword;
}
