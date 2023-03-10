package com.bezkoder.springjwt.controllers;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.JoinLobbyDetails;
import com.bezkoder.springjwt.DTO.PlayerDetails;
import com.bezkoder.springjwt.constants.ApplicationConstants;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.Message;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aditya Patil
 * @Date 11-03-2023
 */

//TODO: here we need to add logic
@RestController
@RequestMapping("/badamsatti")
public class WebSocketHandler {

    @Autowired
    AuthController authController;

    @Autowired
    LobbyController lobbyController;

    @Autowired
    CardProcessingResource cardProcessingResource;

    @Autowired
    NextPlayerFinder nextPlayerFinder;

    @GetMapping("/getmessage")
    public ResponseEntity<?> handleRequest(@RequestBody Message message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        if (message.getMsg().equals(ApplicationConstants.SIGNUP_USER)) {
            try {
                SignupRequest signupRequest = objectMapper.readValue(objectMapper.writeValueAsString(message.getRequestBody()), SignupRequest.class);
                Object object = authController.registerUser(signupRequest);

                Message sendMessage = new Message();
                sendMessage.setMsg(ApplicationConstants.SIGNUP_USER);
                sendMessage.setRequestBody(object);
                return new ResponseEntity<>(sendMessage, HttpStatus.OK);
            } catch (Exception e) {
                Message sendMessage = new Message();
                sendMessage.setStatusCode(500);
                sendMessage.setErrorMsg(e.getMessage());
                return new ResponseEntity<>(sendMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (message.getMsg().equals(ApplicationConstants.SIGNIN_USER)) {
            try {
                //LoginRequest
                LoginRequest loginRequest = objectMapper.readValue(objectMapper.writeValueAsString(message.getRequestBody()), LoginRequest.class);
                Object object = authController.authenticateUser(loginRequest);

                Message sendMessage = new Message();
                sendMessage.setMsg(ApplicationConstants.SIGNIN_USER);
                sendMessage.setRequestBody(object);
                return new ResponseEntity<>(sendMessage, HttpStatus.OK);
            } catch (Exception e) {
                Message sendMessage = new Message();
                sendMessage.setStatusCode(500);
                sendMessage.setErrorMsg(e.getMessage());
                return new ResponseEntity<>(sendMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //-------------------------------------Lobby----------------------------------------------
        try { //727332
            if (message.getMsg().equals(ApplicationConstants.CREATE_LOBBY)) {
                Lobby lobby = objectMapper.readValue(objectMapper.writeValueAsString(message.getRequestBody()), Lobby.class);
                Object object = lobbyController.createLobby(lobby);
                Message sendMessage = new Message();
                sendMessage.setMsg(ApplicationConstants.CREATE_LOBBY);
                sendMessage.setRequestBody(object);
                return new ResponseEntity<>(sendMessage, HttpStatus.OK);
            }
        } catch (Exception e) {
            Message sendMessage = new Message();
            sendMessage.setStatusCode(500);
            sendMessage.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(sendMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            if (message.getMsg().equals(ApplicationConstants.JOIN_LOBBY)) {
                JoinLobbyDetails joinLobbyDetails = objectMapper.readValue(objectMapper.writeValueAsString(message.getRequestBody()), JoinLobbyDetails.class);
                Object object = lobbyController.joinLobby(joinLobbyDetails.getLobbyId(), joinLobbyDetails.getUserId());
                Message sendMessage = new Message();
                sendMessage.setMsg(ApplicationConstants.JOIN_LOBBY);
                sendMessage.setRequestBody(object);
                return new ResponseEntity<>(sendMessage, HttpStatus.OK);
            }
        } catch (Exception e) {
            Message sendMessage = new Message();
            sendMessage.setStatusCode(500);
            sendMessage.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(sendMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //-------------------------------------------- DISTRIBUTE ----------------------------------------------------

        try {
            if (message.getMsg().equals(ApplicationConstants.CARD_DISTRIBUTE)) {
                JoinLobbyDetails joinLobbyDetails = objectMapper.readValue(objectMapper.writeValueAsString(message.getRequestBody()), JoinLobbyDetails.class);
                Object object = cardProcessingResource.shuffleAndDistribute(joinLobbyDetails.getLobbyId());
                Message sendMessage = new Message();
                sendMessage.setMsg(ApplicationConstants.CARD_DISTRIBUTE);
                sendMessage.setRequestBody(object);
                return new ResponseEntity<>(sendMessage, HttpStatus.OK);
            }
        } catch (Exception e) {
            Message sendMessage = new Message();
            sendMessage.setStatusCode(500);
            sendMessage.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(sendMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        try {
            if (message.getMsg().equals(ApplicationConstants.FIND_NEXT_PLAYER)) {
                PlayerDetails playerDetails = objectMapper.readValue(objectMapper.writeValueAsString(message.getRequestBody()), PlayerDetails.class);
                Object object = nextPlayerFinder.findNextPlayer(playerDetails);
                Message sendMessage = new Message();
                sendMessage.setMsg(ApplicationConstants.FIND_NEXT_PLAYER);
                sendMessage.setRequestBody(object);
                return new ResponseEntity<>(sendMessage, HttpStatus.OK);
            }
        } catch (Exception e) {
            Message sendMessage = new Message();
            sendMessage.setStatusCode(500);
            sendMessage.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(sendMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return null;

    }
}

