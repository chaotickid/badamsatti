package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.PlayerDetails;
import com.bezkoder.springjwt.services.PlayerFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aditya Patil
 * @Date 08-03-2023
 */

@RestController
@RequestMapping("/api/v1/player")
public class NextPlayerFinder {


    @Autowired
    private PlayerFinderService playerFinderService;

    @PostMapping("/findnext")
    public PlayerDetails findNextPlayer(@RequestBody PlayerDetails playerDetails){
        return playerFinderService.getListOfSequenceOfUserId(playerDetails);
    }

}
