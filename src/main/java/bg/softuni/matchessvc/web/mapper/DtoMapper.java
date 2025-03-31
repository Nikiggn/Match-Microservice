package bg.softuni.matchessvc.web.mapper;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.model.MatchStatus;
import bg.softuni.matchessvc.web.dto.MatchCreation;
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

//        if (entity.getStatus() == MatchStatus.UPCOMING){
//            return MatchUpcomingResponse.builder()
//                    .homeTeam(entity.getHomeTeamId())
//                    .awayTeam(entity.getAwayTeamId())
//                    .date(entity.getDate())
//                    .status(entity.getStatus())
//                    .build();
//        }
//
//
//        return MatchResponse.builder()
//                .homeTeam(entity.getHomeTeamId())
//                .awayTeam(entity.getAwayTeamId())
//                .date(entity.getDate())
//                .status(entity.getStatus())
//                .homeScore(entity.getHomeScore())
//                .awayScore(entity.getAwayScore())
//
//                .build();
    }
}
