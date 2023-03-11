package com.bezkoder.springjwt.exceptions;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Aditya Patil
 * @Date 07-03-2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadamSattiExceptio extends RuntimeException{

    private String messageType;
    private Instant timeStamp;

    private String statusCode;

    private int statusCodeValue;


    public BadamSattiExceptio(String msg, String messageType){
        super(msg);
        this.messageType = messageType;
        this.timeStamp = Instant.now();
        this.statusCode = String.valueOf("INTERNAL_SERVER_ERROR");
        this.statusCodeValue = 500;
    }
}

