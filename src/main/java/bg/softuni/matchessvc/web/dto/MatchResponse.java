package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class MatchResponse {
    private UUID homeTeam;
    private UUID awayTeam;
    private LocalDateTime date;
    private MatchStatus status;

    //Optional
    private int homeScore;
    private int awayScore;
    private List<Long> homePlayersIds;
    private List<Long> awayPlayersIds;
    private Long matchMvpId;
}
