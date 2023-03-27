package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.FirstDistribution;
import com.bezkoder.springjwt.constants.ApplicationConstants;
import com.bezkoder.springjwt.models.Card;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.CardRepository;
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

    @Autowired
    private CardRepository cardRepository;

    public int distributeCards(int lobbyJoinCode) {
        FirstDistribution firstDistribution = new FirstDistribution();

        List<User> userList = new ArrayList<>();
        Lobby lobby = lobbyRepository.findByLobbyCode(lobbyJoinCode);

        userList = lobby.getUserList();
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> numbers = new ArrayList<>();



        if(lobby.getLobbyStatus().equals(ApplicationConstants.CLOSED)){
            throw new RuntimeException("Card distribution is already completed");
        }
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


        System.out.println("Printing card list after distribution");
        for(int i=0; i<userList.size(); i++){
            userList.get(i).getCardIdList().forEach(t -> System.out.print(t.getCardNumber()+","));
            System.out.println();
            userList.get(i).setActivityStatus(ApplicationConstants.PLAYING);
        }
        lobby.setLobbyStatus(ApplicationConstants.CLOSED);
        return badamSattiUserId;
    }

    public int generate6DigitCode(){
        int code = (int) (Math.random() * 900000) + 100000; // Generates a random integer between 100000 and 999999
        log.debug("Code: " + code);
        return code; // Convert integer to string and return
    }

    public void makeAllPlayed(int lobbyId, int userId){
        List<Card> cardList = cardRepository.findByLobbyJoinCodeAndUserUserId(lobbyId, userId);

        for(int i=0; i< cardList.size(); i++){
            cardList.get(i).setCardPlacedStatus("PLAYED");
        }
    }


    public void getAllCards(Integer joinCode){
        List<Card> cardList = cardRepository.findAll();

        for(int i=0; i<cardList.size(); i++){

            if(joinCode !=null && joinCode == cardList.get(i).getLobbyJoinCode()) {
                System.out.println(cardList.get(i).getId() + "  " + cardList.get(i).getCardNumber()
                        + "  " + cardList.get(i).getCardPlacedStatus() + "  " +
                        cardList.get(i).getLobbyJoinCode() + "  " + cardList.get(i).getUser().getUserId()+"  <-------------");
            }else{
                System.out.println(cardList.get(i).getId() + "  " + cardList.get(i).getCardNumber()
                        + "  " + cardList.get(i).getCardPlacedStatus() + "  " +
                        cardList.get(i).getLobbyJoinCode() + "  " + cardList.get(i).getUser().getUserId());
            }
        }
    }
}
