package com.bezkoder.springjwt.DTO;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aditya Patil
 * @Date 12-03-2023

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int userId;
    private String userName;
}
