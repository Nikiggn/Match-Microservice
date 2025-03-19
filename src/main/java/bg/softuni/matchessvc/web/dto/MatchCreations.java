package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchCreations {
    private Long homeTeamId;
    private Long awayTeamId;
    private LocalDateTime date;
    private MatchStatus status;
    private int homeScore;  // Nullable for non-completed matches
    private int awayScore;  // Nullable for non-completed matches
}
