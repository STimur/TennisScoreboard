package org.timur.roadmap.tennisscoreboard;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchDao matchDao;

    public MatchService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public List<Match> getAllMatches() {
        return matchDao.findAll();
    }
}