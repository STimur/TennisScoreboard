package org.timur.roadmap.tennisscoreboard;

import org.springframework.stereotype.Service;

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
}