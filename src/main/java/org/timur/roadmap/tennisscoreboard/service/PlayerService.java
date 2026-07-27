package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.dao.PlayerDao;
import org.timur.roadmap.tennisscoreboard.entity.Player;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<Player> getAllPlayers() {
        return playerDao.findAll();
    }

    public String findOrCreate(String name) {
        Player player = playerDao.findByName(name)
                .orElseGet(() -> playerDao.save(new Player(name)));

        return player.getName();
    }
}