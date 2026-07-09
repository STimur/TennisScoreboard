package org.timur.roadmap.tennisscoreboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.timur.roadmap.tennisscoreboard.dto.ScoreResponse;

@Mapper(componentModel = "spring")
public interface OngoingMatchMapper {

    @Mapping(target = "firstPlayerName", source = "firstPlayer.name")
    @Mapping(target = "secondPlayerName", source = "secondPlayer.name")
    @Mapping(target = "firstPlayerPoints", source = "score.firstPlayerPoints")
    @Mapping(target = "secondPlayerPoints", source = "score.secondPlayerPoints")
    @Mapping(target = "firstPlayerGames", source = "score.firstPlayerGames")
    @Mapping(target = "secondPlayerGames", source = "score.secondPlayerGames")
    @Mapping(target = "firstPlayerSets", source = "score.firstPlayerSets")
    @Mapping(target = "secondPlayerSets", source = "score.secondPlayerSets")
    @Mapping(target = "firstPlayerTieBreakPoints", source = "score.firstPlayerTieBreakPoints")
    @Mapping(target = "secondPlayerTieBreakPoints", source = "score.secondPlayerTieBreakPoints")
    @Mapping(target = "winnerName", ignore = true)
    ScoreResponse toDto(OngoingMatch match);
}