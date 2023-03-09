package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.PlayerDetails;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Aditya Patil
 * @Date 08-03-2023
 */

@Service
@Transactional
public class PlayerFinderService {

    @Autowired
    private LobbyRepository lobbyRepository;

    public PlayerDetails getListOfSequenceOfUserId(PlayerDetails playerDetails) {
        Lobby lobby = lobbyRepository.findByLobbyCode(playerDetails.getLobbyJoinCode());
        List<User> userList = lobby.getUserList();

         userList.forEach(t-> System.out.println(t.getUserId()));
        LinkedList<Integer> listOfUserId = new LinkedList<>();

        for (int i = 0; i < userList.size(); i++) {
            listOfUserId.add(userList.get(i).getUserId());
        }
        System.out.println("Liked list "+ listOfUserId);
        int currentUserId = playerDetails.getCurrentUserId();
        playerDetails.setLastPlayedUserId(currentUserId);

        System.out.println("Current user id: "+ currentUserId);
        //2 3 4
        while (true) {
            if (listOfUserId.get(0) != currentUserId) {
                int first = listOfUserId.removeFirst();
                listOfUserId.addLast(first);
                System.out.println("wwwwwwwww" +listOfUserId);
                continue;
            }
            int buffer = listOfUserId.removeFirst();
            listOfUserId.addLast(buffer);
            currentUserId = listOfUserId.getFirst();
            break;

        }
        playerDetails.setCurrentUserId(currentUserId);
        return playerDetails;
    }
}
