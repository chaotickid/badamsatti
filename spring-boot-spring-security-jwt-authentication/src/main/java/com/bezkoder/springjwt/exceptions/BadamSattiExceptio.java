package com.bezkoder.springjwt.exceptions;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Aditya Patil
 * @Date 07-03-2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadamSattiExceptio extends Exception{
    private String message;
    private LocalDate date= LocalDate.now();

    public BadamSattiExceptio(String msg){
        super(msg);
        this.message = msg;
    }
}

