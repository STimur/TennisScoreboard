package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.dao.PlayerDao;
import org.timur.roadmap.tennisscoreboard.entity.Player;
import org.timur.roadmap.tennisscoreboard.infrastructure.TransactionRunner;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerDao playerDao;
    private final TransactionRunner txRunner;

    public PlayerService(PlayerDao playerDao, TransactionRunner txRunner) {
        this.playerDao = playerDao;
        this.txRunner = txRunner;
    }

    public List<Player> getAllPlayers() {
        return txRunner.runInTransaction(playerDao::findAll);
    }

    public Player findOrCreate(String name) {
        return txRunner.runInTransaction(() ->
                playerDao.findByName(name).orElseGet(() -> playerDao.save(new Player(name))));
    }
}