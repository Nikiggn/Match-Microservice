package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MatchUpcomingResponse {
    private Long homeTeam;
    private Long awayTeam;
    private LocalDateTime date;
    private MatchStatus status;
}
