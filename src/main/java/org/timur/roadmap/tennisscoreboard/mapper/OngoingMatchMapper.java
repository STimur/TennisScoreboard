package org.timur.roadmap.tennisscoreboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.timur.roadmap.tennisscoreboard.domain.MatchScore;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.timur.roadmap.tennisscoreboard.dto.PlayerScoreDto;
import org.timur.roadmap.tennisscoreboard.dto.ScoreResponse;

@Mapper(componentModel = "spring")
public interface OngoingMatchMapper {

    @Mapping(target = "firstPlayer", source = "score", qualifiedByName = "firstPlayer")
    @Mapping(target = "secondPlayer", source = "score", qualifiedByName = "secondPlayer")
    @Mapping(target = "winnerName", source = "score.winnerName")
    ScoreResponse toDto(OngoingMatch match);

    @Named("firstPlayer")
    @Mapping(target = "name", source = "firstPlayerName")
    @Mapping(target = "points", source = "firstPlayerPoints")
    @Mapping(target = "games", source = "firstPlayerGames")
    @Mapping(target = "sets", source = "firstPlayerSets")
    @Mapping(target = "tieBreakPoints", source = "firstPlayerTieBreakPoints")
    PlayerScoreDto toFirstPlayerDto(MatchScore score);

    @Named("secondPlayer")
    @Mapping(target = "name", source = "secondPlayerName")
    @Mapping(target = "points", source = "secondPlayerPoints")
    @Mapping(target = "games", source = "secondPlayerGames")
    @Mapping(target = "sets", source = "secondPlayerSets")
    @Mapping(target = "tieBreakPoints", source = "secondPlayerTieBreakPoints")
    PlayerScoreDto toSecondPlayerDto(MatchScore score);
}