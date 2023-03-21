package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.FirstDistribution;
import com.bezkoder.springjwt.constants.ApplicationConstants;
import com.bezkoder.springjwt.models.Card;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.LobbyRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Aditya Patil
 * @Date 02-03-2023
 */

@Service
@Slf4j
@Transactional
public class CardServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LobbyRepository lobbyRepository;

    public void distributeCards(int lobbyJoinCode) {
        FirstDistribution firstDistribution = new FirstDistribution();

        List<User> userList = new ArrayList<>();
        Lobby lobby = lobbyRepository.findByLobbyCode(lobbyJoinCode);
        lobby.setLobbyStatus(ApplicationConstants.CLOSED);
        userList = lobby.getUserList();
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 52; i++) {
            numbers.add(i);
        }

        //if no of coonected people are greater than >= 6 then use two cats
        //lobby.setNoOfCatsForCurrentLobby(2);
        if(lobby.getNoOfConnectPeople() >= 6){
            for (int i = 1; i <= 52; i++) {
                numbers.add(i);
            }
        }

        int badamSattiUserId = -1;

        log.debug("User size: "+ userList);
        log.debug("Added numbers: " + numbers);
        Collections.shuffle(numbers);
        int k = 0;
        while (true) {
            if (k == numbers.size()) {
                break;
            }
            for (int j = 0; j < userList.size(); j++) {
                if (k == numbers.size()) {
                    break;
                }
                if(numbers.get(k) == 7){
                    badamSattiUserId = userList.get(j).getUserId();
                }
                Card card = new Card(numbers.get(k));
                card.setLobbyJoinCode(lobby.getLobbyCode());
                userList.get(j).addCard(card);
                k++;
            }

        }
//        HashMap<Integer, List<Integer>> hashMap =  new HashMap<>();

//        for(int i=0; i<userList.size(); i++){
//            hashMap.put(userList.get(i).getUserId(), userList.get(i).getCardIdList());
//        }
//        firstDistribution.setBadamSattiUserId(badamSattiUserId);
//        firstDistribution.setDistributedCardList(hashMap);
//        return firstDistribution;
    }

    public int generate6DigitCode(){
        int code = (int) (Math.random() * 900000) + 100000; // Generates a random integer between 100000 and 999999
        log.debug("Code: " + code);
        return code; // Convert integer to string and return
    }
}
