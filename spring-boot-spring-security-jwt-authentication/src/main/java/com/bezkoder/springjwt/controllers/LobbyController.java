package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.exceptions.BadamSattiExceptio;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aditya Patil
 * @Date 06-03-2023
 */
@RestController
@RequestMapping("/api/v1/lobby")
public class LobbyController {


    @Autowired
    private LobbyService lobbyService;

    @PostMapping("/createlobby")
    public ResponseEntity<Lobby> createLobby(@RequestBody Lobby lobby){
        return new ResponseEntity<>(lobbyService.createLobby(lobby), HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinLobby(@RequestParam (name = "joincode") int joinCode) throws BadamSattiExceptio {
        return lobbyService.joinLobby(joinCode);
    }
}
