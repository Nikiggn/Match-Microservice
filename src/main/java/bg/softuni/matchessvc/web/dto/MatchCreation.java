package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchCreation {
    private UUID homeTeamId;
    private UUID awayTeamId;
    private LocalDateTime date;
    private MatchStatus status;
    private int homeScore;
    private int awayScore;
    private UUID refereeId;
}
