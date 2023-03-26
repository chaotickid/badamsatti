package com.bezkoder.springjwt.services;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.DTO.ForgotPassword;
import com.bezkoder.springjwt.DTO.PointsDto;
import com.bezkoder.springjwt.DTO.UserCardDetailsDto;
import com.bezkoder.springjwt.DTO.UserPoints;
import com.bezkoder.springjwt.constants.ApplicationConstants;
import com.bezkoder.springjwt.models.Card;
import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.CardRepository;
import com.bezkoder.springjwt.repository.LobbyRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Aditya Patil
 * @Date 13-03-2023
 */

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LobbyRepository lobbyRepository;

    @Autowired
    CardRepository cardRepository;

    public HashMap<Integer, Integer> cardPoints(){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,13);//ekka
        map.put(2,2);
        map.put(3,3);
        map.put(4,4);
        map.put(5,5);
        map.put(6,6);
        map.put(7,7);
        map.put(8,8);
        map.put(9,9);
        map.put(10,10);
        map.put(11,11);
        map.put(12,12);
        map.put(13,13);
        map.put(14,13);//ekka
        map.put(15,2);
        map.put(16,3);
        map.put(17,4);
        map.put(18,5);
        map.put(19,6);
        map.put(20,7);
        map.put(21,8);
        map.put(22,9);
        map.put(23,10);
        map.put(24,11);
        map.put(25,12);
        map.put(26,13);
        map.put(27,13);//ekka
        map.put(28,2);
        map.put(29,3);
        map.put(30,4);
        map.put(31,5);
        map.put(32,6);
        map.put(33,7);
        map.put(34,8);
        map.put(35,9);
        map.put(36,10);
        map.put(37,11);
        map.put(38,12);
        map.put(39,13);
        map.put(40,13);//ekka
        map.put(41,2);
        map.put(42,3);
        map.put(43,4);
        map.put(44,5);
        map.put(45,6);
        map.put(46,7);
        map.put(47,8);
        map.put(48,9);
        map.put(49,10);
        map.put(50,11);
        map.put(51,12);
        map.put(52,13);
        return map;
    }

    public User updatePassword(int id, ForgotPassword forgotPassword){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        if(user.getForgotPasswordCode() != forgotPassword.getVerificationCode()){
            throw new RuntimeException("Invalid verification id");
        }
        user.setPassword(passwordEncoder.encode(forgotPassword.getNewPassword()));
        return user;
    }


    public UserCardDetailsDto getUserCardDetails(int id, int lobbyId){

        UserCardDetailsDto userCardDetailsDto = new UserCardDetailsDto();
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        userCardDetailsDto.setUserId(user.getUserId());
        userCardDetailsDto.setUserName(user.getUsername());

        List<Integer> cardIntList = new ArrayList<>();
        List<Card> cardList = cardRepository.findByLobbyJoinCodeAndUserUserId(lobbyId, user.getUserId());

        if(!CollectionUtils.isEmpty(cardList)) {
            for (int i = 0; i < cardList.size(); i++) {
                cardIntList.add(cardList.get(i).getCardNumber());
            }
        }
        userCardDetailsDto.setCardList(cardIntList);
        return userCardDetailsDto;

    }

    public PointsDto calculatePoints(int joinCode){

        //actual points to .png photos
        HashMap<Integer, Integer> map = cardPoints();

        //lift lobby
        Lobby lobby = lobbyRepository.findByLobbyCode(joinCode);

        //fetch userList
        List<User> userList = lobby.getUserList();
        userList.forEach(t-> System.out.println(t.getUserId()));

        PointsDto pointsDto= new PointsDto();

        for(int i=0; i<userList.size(); i++){
            UserPoints userPoints = new UserPoints();
            List<Card> cardList = userList.get(i).getCardIdList();
            cardList.forEach(t-> System.out.print("[ "+ t.getCardNumber() + " "));
            System.out.print(" ]");
            int result= 0;
            for(int j=0; j<cardList.size(); j++){
                if(cardList.get(j).getCardPlacedStatus().equals(ApplicationConstants.NOT_PLAYED)) {
                    System.out.println("cardListstatus :" + cardList.get(j).getCardPlacedStatus());
                    System.out.println("Userid: " + userList.get(i).getUserId() + ", not played card actual points: "+ map.get(cardList.get(j).getCardNumber()) + ", png no: "+ (cardList.get(j).getCardNumber()));
                    result = result + map.get(cardList.get(j).getCardNumber());
                }
            }
            userPoints.setUserId(userList.get(i).getUserId());
            userPoints.setUserName(userList.get(i).getUsername());
            userPoints.setTotalPoints(result);
            pointsDto.getUserPointsList().add(userPoints);
        }

        for(int i=0; i<userList.size(); i++){
            userList.get(i).setActivityStatus(ApplicationConstants.INACTIVE);
        }
        return pointsDto;

    }

}
