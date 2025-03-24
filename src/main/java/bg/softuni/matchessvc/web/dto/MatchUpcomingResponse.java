package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MatchUpcomingResponse {
    private UUID homeTeam;
    private UUID awayTeam;
    private LocalDateTime date;
    private MatchStatus status;
}
