package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchService {

    private final Map<UUID, OngoingMatch> matches =
            new ConcurrentHashMap<>();

    public void add(OngoingMatch match) {
        matches.put(match.getId(), match);
    }

    public Optional<OngoingMatch> find(UUID id) {
        return Optional.of(matches.get(id));
    }

    public void remove(UUID id) {

    }
}