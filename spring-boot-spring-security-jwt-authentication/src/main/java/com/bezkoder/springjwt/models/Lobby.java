package com.bezkoder.springjwt.models;
/**
 * Copyright © 2023 Mavenir Systems
 */


import com.bezkoder.springjwt.DTO.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Aditya Patil
 * @Date 06-03-2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lobbyId;

    @NotNull
    private Integer lobbySize;

    @NotNull
    private Integer lobbyOwnerId;

    @Column(unique = true)
    private int lobbyCode;

    private int noOfConnectPeople;

    private String lobbyStatus="INACTIVE";

    private String timeStampOfLobbyCreation;

    private int noOfCatsForCurrentLobby= 1;

//    @Transient
//    private LinkedList<Integer> sequenceOfUserId= new LinkedList<>();
    @Transient
    private List<UserDto> listOfConnectedUsers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<User> userList = new ArrayList<>();


    //helper methods
    public void addUserInTheLobby(User user){
        this.getUserList().add(user);
        user.setLobby(this);
    }

    public void removeUserFromLobby(User user){
        this.removeUserFromLobby(user);
        user.setLobby(null);
    }

}
