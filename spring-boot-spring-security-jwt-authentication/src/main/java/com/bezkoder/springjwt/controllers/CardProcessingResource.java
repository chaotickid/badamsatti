package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.FirstDistribution;
import com.bezkoder.springjwt.services.CardServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author Aditya Patil
 * @Date 02-03-2023
 */
@RestController
@RequestMapping("/api/v1/cardprocessing")
public class CardProcessingResource {

    @Autowired
    private CardServices cardDistributionService;

    @GetMapping("/distribute")
    public void shuffleAndDistribute(@RequestParam (name = "joincode") int joinCode){
         cardDistributionService.distributeCards(joinCode);
    }

}

