package com.bezkoder.springjwt.DTO;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aditya Patil
 * @Date 16-03-2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsDto {
    private List<UserPoints> userPointsList=new ArrayList<>();
}
