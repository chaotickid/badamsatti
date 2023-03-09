package com.bezkoder.springjwt.repository;
/**
 * Copyright © 2023 Mavenir Systems
 */

import com.bezkoder.springjwt.models.Lobby;
import com.bezkoder.springjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aditya Patil
 * @Date 06-03-2023
 */
public interface LobbyRepository extends JpaRepository<Lobby, Integer> {
    public Lobby findByLobbyCode(int lobbyCode);
}
