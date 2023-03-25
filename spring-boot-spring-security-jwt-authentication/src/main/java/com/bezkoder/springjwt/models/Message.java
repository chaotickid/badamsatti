package com.bezkoder.springjwt.models;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aditya Patil
 * @Date 11-03-2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private String msg;

    private Object requestBody;

    private Object badamSattiOwnerId;

}
