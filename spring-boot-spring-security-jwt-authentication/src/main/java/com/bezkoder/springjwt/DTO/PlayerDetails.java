package com.bezkoder.springjwt.DTO;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aditya Patil
 * @Date 08-03-2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDetails {
    private int cardPlayed;
    private int currentUserId;
    private int lastPlayedUserId;
    private int lobbyJoinCode;
    private int noOfCardsRemainig;
}
