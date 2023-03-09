package com.bezkoder.springjwt.exceptions;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Aditya Patil
 * @Date 07-03-2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject {
    private String timeStamp;
    private String errorMsg;
    private HttpStatus httpStatus;
}
