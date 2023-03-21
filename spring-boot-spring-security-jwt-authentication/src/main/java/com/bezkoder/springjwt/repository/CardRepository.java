package com.bezkoder.springjwt.repository;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Aditya Patil
 * @Date 21-03-2023
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    public List<Card> findByLobbyJoinCodeAndUserUserId(int lobbyJoinCode, int userUserId);
}
