package org.timur.roadmap.tennisscoreboard.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchRequest;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchResponse;
import org.timur.roadmap.tennisscoreboard.dto.FinishedMatchesResponse;
import org.timur.roadmap.tennisscoreboard.dto.PointRequest;
import org.timur.roadmap.tennisscoreboard.dto.ScoreResponse;
import org.timur.roadmap.tennisscoreboard.service.MatchService;

import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMatchResponse createMatch(
            @Valid @RequestBody CreateMatchRequest request
    ) {
        return matchService.createMatch(request);
    }

    @PostMapping("/{uuid}/point")
    public ScoreResponse addPoint(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody PointRequest request
    ) {
        return matchService.addPoint(uuid, request);
    }

    @GetMapping("/{uuid}")
    public ScoreResponse getScore(
            @PathVariable("uuid") UUID uuid) {
        return matchService.getScore(uuid);
    }

    @GetMapping
    public FinishedMatchesResponse getMatches(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "player_name", required = false) String playerName
    ) {
        return matchService.getFinishedMatches(page, playerName);
    }
}