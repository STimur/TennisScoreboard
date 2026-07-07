package org.timur.roadmap.tennisscoreboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.timur.roadmap.tennisscoreboard.entity.Player;
import org.timur.roadmap.tennisscoreboard.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> players() {
        return playerService.getAllPlayers();
    }
}