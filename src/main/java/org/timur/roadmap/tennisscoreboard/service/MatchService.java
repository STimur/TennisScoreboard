package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchRequest;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchResponse;
import org.timur.roadmap.tennisscoreboard.dto.MatchDto;
import org.timur.roadmap.tennisscoreboard.dao.MatchDao;
import org.timur.roadmap.tennisscoreboard.entity.Player;
import org.timur.roadmap.tennisscoreboard.mapper.MatchMapper;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchDao matchDao;
    private final MatchMapper matchMapper;
    private final PlayerService playerService;
    private final OngoingMatchService ongoingMatchService;

    public MatchService(MatchDao matchDao, MatchMapper matchMapper,
                        PlayerService playerService, OngoingMatchService ongoingMatchService) {
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
        this.playerService = playerService;
        this.ongoingMatchService = ongoingMatchService;
    }

    public List<MatchDto> getAllMatches() {
        return matchDao.findAll()
                .stream()
                .map(matchMapper::toDto)
                .toList();
    }

    public CreateMatchResponse createMatch(CreateMatchRequest request) {

        Player firstPlayer = playerService.findOrCreate(request.firstPlayerName());
        Player secondPlayer = playerService.findOrCreate(request.secondPlayerName());

        UUID id = UUID.randomUUID();

        OngoingMatch match = new OngoingMatch(
                id,
                firstPlayer,
                secondPlayer
        );

        ongoingMatchService.add(match);

        return new CreateMatchResponse(id);
    }
}