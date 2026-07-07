package org.timur.roadmap.tennisscoreboard.service;

import org.springframework.stereotype.Service;
import org.timur.roadmap.tennisscoreboard.dto.MatchDto;
import org.timur.roadmap.tennisscoreboard.entity.Match;
import org.timur.roadmap.tennisscoreboard.dao.MatchDao;
import org.timur.roadmap.tennisscoreboard.mapper.MatchMapper;

import java.util.List;

@Service
public class MatchService {

    private final MatchDao matchDao;
    private final MatchMapper matchMapper;

    public MatchService(MatchDao matchDao, MatchMapper matchMapper) {
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
    }

    public List<MatchDto> getAllMatches() {
        return matchDao.findAll()
                .stream()
                .map(matchMapper::toDto)
                .toList();
    }
}