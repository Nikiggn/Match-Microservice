package bg.softuni.matchessvc.web.dto;

import bg.softuni.matchessvc.model.MatchStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchCreation {
    private UUID homeTeamId;
    private UUID awayTeamId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private MatchStatus status;
    private int homeScore;
    private int awayScore;
    private UUID refereeId;
}
