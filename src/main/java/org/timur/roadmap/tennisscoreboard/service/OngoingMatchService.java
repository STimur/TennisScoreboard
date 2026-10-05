package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchService {

    // Нет интерфейса для этого класса. (см. файл "service.md" в этом же пакете)

    private final Map<UUID, OngoingMatch> matches =
            new ConcurrentHashMap<>();

    // Хранилище может само создавать ID для матча (по аналогии с БД) и возвращать его из этого метода.
    public void add(OngoingMatch match) {
        matches.put(match.getId(), match);
    }

    public Optional<OngoingMatch> find(UUID id) {
        return Optional.ofNullable(matches.get(id));
    }

    public void remove(UUID id) {
        matches.remove(id);
    }
}