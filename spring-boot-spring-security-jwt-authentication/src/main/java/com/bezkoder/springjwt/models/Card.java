package com.bezkoder.springjwt.models;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Aditya Patil
 * @Date 16-03-2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cardNumber;

    private String cardPlacedStatus="NOT_PLAYED";

    private int lobbyJoinCode;

    @ManyToOne
    private User user;

    public Card(int number){
        this.cardNumber = number;
    }

}
