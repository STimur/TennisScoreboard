package org.timur.roadmap.tennisscoreboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.timur.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.timur.roadmap.tennisscoreboard.dto.MatchDto;
import org.timur.roadmap.tennisscoreboard.entity.Match;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "player1", source = "player1.name")
    @Mapping(target = "player2", source = "player2.name")
    @Mapping(target = "winner", source = "winner.name")
    MatchDto toDto(Match match);

    @Mapping(target = "firstPlayerName", source = "player1.name")
    @Mapping(target = "secondPlayerName", source = "player2.name")
    @Mapping(target = "winnerName", source = "winner.name")
    FinishedMatchDto toFinishedDto(Match match);
}