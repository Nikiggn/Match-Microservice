package bg.softuni.matchessvc.web.mapper;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.model.MatchStatus;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.dto.MatchForProcessing;
import bg.softuni.matchessvc.web.dto.MatchResponse;
import bg.softuni.matchessvc.web.dto.MatchUpcomingResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static MatchCreation fromMatchCreation(Match entity){
        return MatchCreation.builder()
                .homeTeamId(entity.getHomeTeamId())
                .awayTeamId(entity.getAwayTeamId())
                .date(entity.getDate())
                .status(entity.getStatus())
                .homeScore(entity.getHomeScore())
                .awayScore(entity.getAwayScore())
                .refereeId(entity.getRefereeId())
                .build();

    }

    public static MatchForProcessing fromMatchForProcessing(Match entity){
        return MatchForProcessing.builder()
                .id(entity.getId())
                .homeClubId(entity.getHomeTeamId())
                .awayClubId(entity.getAwayTeamId())
                .status(entity.getStatus())
                .homeScore(entity.getHomeScore())
                .awayScore(entity.getAwayScore())
                .build();
    }
}
