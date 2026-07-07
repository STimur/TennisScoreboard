package org.timur.roadmap.tennisscoreboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.timur.roadmap.tennisscoreboard.dto.MatchDto;
import org.timur.roadmap.tennisscoreboard.entity.Match;
import org.timur.roadmap.tennisscoreboard.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<MatchDto> players() {
        return matchService.getAllMatches();
    }
}