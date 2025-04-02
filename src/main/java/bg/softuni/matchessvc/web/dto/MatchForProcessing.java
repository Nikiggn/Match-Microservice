package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchForProcessing {
    private UUID id;
    private UUID homeClubId;
    private UUID awayClubId;
    private MatchStatus status;
    private int homeScore;
    private int awayScore;
    private boolean processed;
}
