package com.bezkoder.springjwt.DTO;

/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

/**
 * @author Aditya Patil
 * @Date 06-03-2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstDistribution {
    private int badamSattiUserId;
    private HashMap<Integer, List<Integer>> distributedCardList = new HashMap<>();
}
