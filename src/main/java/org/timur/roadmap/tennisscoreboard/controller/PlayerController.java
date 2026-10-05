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

    // Этот контроллер не используется в работе приложения.
        // Код, не предназначенный для работы приложения, стоит удалять перед коммитом.

    // TODO: Контроллер работает с JPA Entity и передаёт их во View.
        // Это нарушает границы между слоями приложения и Принцип разделения ответственности
        // (см. файл "separation-of-concerns-principle.md" в этом же пакете)
        // Контроллер не должен работать с JPA Entity и передавать их во View.
        // Вместо этого он должен "общаться" с другими слоями через DTO.

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // TODO: Метод возвращает список вообще всех игроков в приложении без пагинации.
        // Это неудобно для клиента, а также при большом количестве игроков это может к OutOfMemoryError.
    @GetMapping
    public List<Player> players() {
        return playerService.getAllPlayers();
    }
}