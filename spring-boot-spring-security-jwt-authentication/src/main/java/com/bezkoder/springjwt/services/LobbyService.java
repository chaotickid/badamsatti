package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.UserDto;
import com.bezkoder.springjwt.constants.ApplicationConstants;
import com.bezkoder.springjwt.exceptions.ErrorObject;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.LobbyRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Aditya Patil
 * @Date 06-03-2023
 */
@Service
@Slf4j
@Transactional
public class LobbyService {

    @Autowired
    private CardServices cardServices;

    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public List<Integer> userList;


    public Lobby createLobby(Lobby lobby) {
        Lobby lobby1 = new Lobby();
        //UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1 = userRepository.findById(lobby.getLobbyOwnerId()).orElseThrow(() -> new RuntimeException("User with id not found: " + lobby.getLobbyOwnerId()));
        lobby1.setLobbyOwnerId(lobby.getLobbyOwnerId());
        lobby1.setLobbySize(lobby.getLobbySize());
        lobby1.setLobbyCode(cardServices.generate6DigitCode());
        lobby1.setLobbyStatus(ApplicationConstants.WAITING_FOR_PLAYERS);
        lobby1.addUserInTheLobby(user1);
        log.debug("Lobby created successfully by userID " + user1.getUserId() + " name: " + user1.getUsername());
        lobby1.setNoOfConnectPeople(lobby1.getNoOfConnectPeople() + 1);
        lobby1.setTimeStampOfLobbyCreation(String.valueOf(Instant.now()));
        System.out.println("Lobby timing: " + lobby1.getTimeStampOfLobbyCreation());
        lobby1.getListOfConnectedUsers().add(new UserDto(lobby.getLobbyOwnerId(), user1.getUsername()));
//        lobby1.getSequenceOfUserId().add(user1.getUserId());
        lobbyRepository.save(lobby1);
        return lobby1;
    }

    public ResponseEntity<?> joinLobby(int joinCode, int userId)  {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id not found: " + userId));
        System.out.println(user1.getUserId() + " : " + user1.getUsername());
        Lobby lobby = lobbyRepository.findByLobbyCode(joinCode);

        if(lobby.getLobbyStatus().equals(ApplicationConstants.CLOSED)){
            throw new RuntimeException("Lobby already closed.");
        }
//        if(userId == lobby.getLobbyOwnerId()){
//            return new ResponseEntity<>(
//                    new ErrorObject(String.valueOf(Instant.now()),
//                            "This user created lobby and already joined the lobby", HttpStatus.NOT_FOUND)
//                    , HttpStatus.NOT_FOUND);
//        }


        //searching lobby is present or not
        if (lobby == null) {
            throw new RuntimeException("Lobby not found");

        }

        //searching if user is disconnected or somthing is happened in between then return lobby details
        AtomicBoolean flag = new AtomicBoolean(false);
        lobby.getUserList().forEach(t -> {
            if (t.getUserId() == user1.getUserId()) {
                flag.set(true);


            }
        });

        if(flag.get()){ //allowing user to join again
            List<User> userList = lobby.getUserList();
            for(int i=0; i< userList.size(); i++){
                lobby.getListOfConnectedUsers().add(new UserDto(
                        userList.get(i).getUserId(), userList.get(i).getUsername()
                ));
            }
            return new ResponseEntity<>(lobby, HttpStatus.OK);
        }

        //else new user want to join but need to check lobby size
        if (lobby.getUserList().size() == lobby.getLobbySize()) {
            throw new RuntimeException("Lobby is full");
        }

        lobby.addUserInTheLobby(user1);
        lobby.setNoOfConnectPeople(lobby.getNoOfConnectPeople() + 1);

        if(lobby.getNoOfConnectPeople() >= 6){
            log.debug("Setting cat size: "+ 2 + " for "+ lobby.getNoOfConnectPeople()+ " people.");
            lobby.setNoOfCatsForCurrentLobby(2);
        }
        List<User> userList = lobby.getUserList();
        for(int i=0; i< userList.size(); i++){
            lobby.getListOfConnectedUsers().add(new UserDto(
                    userList.get(i).getUserId(), userList.get(i).getUsername()
            ));

        }
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }
}
