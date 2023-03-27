package com.bezkoder.springjwt.DBTesting;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.services.CardServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aditya Patil
 * @Date 26-03-2023
 */

@RestController
@RequestMapping("/test/db")
public class DbTesting {

    @Autowired
    CardServices cardServices;

    @PostMapping("/make-all-played")
    public void makeAllPlayedForUserIdAndLobbyId(@RequestParam int userId, @RequestParam int lobbyId){
        cardServices.makeAllPlayed(lobbyId, userId);
        System.out.println("MADE ALL PLAYED");
    }

    @GetMapping("/get-all-cards")
    public void getAll(@RequestParam (required = false)Integer joinCode){
        cardServices.getAllCards(joinCode);
    }
}
