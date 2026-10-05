package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.dao.PlayerDao;
import org.timur.roadmap.tennisscoreboard.entity.Player;
import org.timur.roadmap.tennisscoreboard.infrastructure.TransactionRunner;

import java.util.List;

@Service
public class PlayerService {

    // Нет интерфейса для этого класса. (см. файл "service.md" в этом же пакете)

    // TODO: Класс передаёт JPA-сущности в контроллер. Это нарушение принципа разделения ответственности между слоями.
        // (см. файл "separation-of-concerns-principle.md" в этом же пакете).
        // Сервисный слой должен служить границей, которая изолирует доменную логику
        // и персистентность (слой долговременного хранения данных) от внешнего мира (например, от контроллеров).
        // Сервис должен возвращать в контроллер только DTO.

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