package org.timur.roadmap.tennisscoreboard.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchRequest;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchResponse;
import org.timur.roadmap.tennisscoreboard.dto.FinishedMatchesResponse;
import org.timur.roadmap.tennisscoreboard.dto.MatchDto;
import org.timur.roadmap.tennisscoreboard.dao.MatchDao;
import org.timur.roadmap.tennisscoreboard.dto.PageResult;
import org.timur.roadmap.tennisscoreboard.dto.PointRequest;
import org.timur.roadmap.tennisscoreboard.dto.ScoreResponse;
import org.timur.roadmap.tennisscoreboard.entity.Match;
import org.timur.roadmap.tennisscoreboard.entity.Player;
import org.timur.roadmap.tennisscoreboard.mapper.MatchMapper;
import org.timur.roadmap.tennisscoreboard.exception.MatchNotFoundException;
import org.timur.roadmap.tennisscoreboard.mapper.OngoingMatchMapper;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchDao matchDao;
    private final MatchMapper matchMapper;
    private final OngoingMatchMapper ongoingMatchMapper;
    private final PlayerService playerService;
    private final OngoingMatchService ongoingMatchService;

    public MatchService(MatchDao matchDao, MatchMapper matchMapper,
                        OngoingMatchMapper ongoingMatchMapper, PlayerService playerService,
                        OngoingMatchService ongoingMatchService, ScoreService scoreService) {
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
        this.ongoingMatchMapper = ongoingMatchMapper;
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

        String firstPlayerName = playerService.findOrCreate(request.firstPlayerName()).getName();
        String secondPlayerName = playerService.findOrCreate(request.secondPlayerName()).getName();

        UUID id = UUID.randomUUID();

        OngoingMatch match = new OngoingMatch(
                id,
                firstPlayerName,
                secondPlayerName
        );

        ongoingMatchService.add(match);

        return new CreateMatchResponse(id);
    }

    public ScoreResponse addPoint(UUID id, @Valid PointRequest request) {
        OngoingMatch ongoingMatch = ongoingMatchService.find(id)
                .orElseThrow(MatchNotFoundException::new);

        synchronized (ongoingMatch) {
            ongoingMatch.addPoint(request.name());

            if (ongoingMatch.isFinished()) {
                saveFinishedMatch(ongoingMatch);
                ongoingMatchService.remove(id);
            }

            return ongoingMatchMapper.toDto(ongoingMatch);
        }
    }

    public ScoreResponse getScore(UUID uuid) {
        OngoingMatch match = ongoingMatchService.find(uuid)
                .orElseThrow(MatchNotFoundException::new);

        return ongoingMatchMapper.toDto(match);
    }

    public FinishedMatchesResponse getFinishedMatches(int page, String playerName) {
        PageResult<Match> matches = matchDao.findFinishedMatches(page, playerName);

        return new FinishedMatchesResponse(
                matches.items()
                        .stream()
                        .map(matchMapper::toFinishedDto)
                        .toList(),
                page,
                matches.totalPages()
        );
    }

    private void saveFinishedMatch(OngoingMatch ongoingMatch) {
        Player firstPlayer = playerService.findOrCreate(ongoingMatch.getFirstPlayerName());
        Player secondPlayer = playerService.findOrCreate(ongoingMatch.getSecondPlayerName());

        Player winner = ongoingMatch.getWinnerName().equals(firstPlayer.getName()) ? firstPlayer : secondPlayer;

        Match finishedMatch = new Match(firstPlayer, secondPlayer, winner);

        matchDao.save(finishedMatch);
    }
}