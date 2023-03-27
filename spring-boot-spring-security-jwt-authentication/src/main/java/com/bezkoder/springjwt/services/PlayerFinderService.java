package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.PlayerDetails;
import com.bezkoder.springjwt.constants.ApplicationConstants;
import com.bezkoder.springjwt.models.Card;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.CardRepository;
import com.bezkoder.springjwt.repository.LobbyRepository;
import com.bezkoder.springjwt.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    public PlayerDetails getListOfSequenceOfUserId(PlayerDetails playerDetails) throws Exception {
        Lobby lobby = lobbyRepository.findByLobbyCode(playerDetails.getLobbyJoinCode());
        List<User> userList = lobby.getUserList();

        userList.forEach(t-> System.out.println(t.getUserId()));
        LinkedList<Integer> listOfUserId = new LinkedList<>();
        System.out.println("List: "+ listOfUserId);

        for (int i = 0; i < userList.size(); i++) {
            listOfUserId.add(userList.get(i).getUserId());
        }
        System.out.println("Liked list "+ listOfUserId);
        int currentUserId = playerDetails.getCurrentUserId();

        //Update that particular card as placed
        User user = userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found"));
        List<Card> cardList = cardRepository.findByLobbyJoinCodeAndUserUserId(playerDetails.getLobbyJoinCode(), currentUserId);
        if(cardList.size()!=0){
            for(int i=0; i< cardList.size(); i++){
                if(cardList.get(i).getCardNumber() == playerDetails.getCardPlayed()){
                    System.out.println("Card Number: " + cardList.get(i).getCardNumber());
                    //cardList.remove(cardList.get(i));
                    cardList.get(i).setCardPlacedStatus(ApplicationConstants.PLAYED);
                    //System.out.println(cardList.get(i).getCardPlacedStatus());
                    break;
                }
            }
        }
        System.out.println("Set card as PLAYED successfully");
        playerDetails.setLastPlayedUserId(currentUserId);
        System.out.println("Current user id: "+ currentUserId);
        //2 3 4
        int counter = 0;
        while (true) {

            if (listOfUserId.get(0) != currentUserId) {
                int first = listOfUserId.removeFirst();
                listOfUserId.addLast(first);
                System.out.println("wwwwwwwww" +listOfUserId);
                counter ++;
                if(counter > listOfUserId.size()+5){
                    System.out.println("Unable to find next user id, Please check the incoming user id");
                    throw new Exception("Unable to find next user Id");
                }
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
