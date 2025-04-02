package bg.softuni.matchessvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "matches")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID homeTeamId;

    @Column(nullable = false)
    private UUID awayTeamId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Column(nullable = false)
    private LocalDateTime date;

    private int homeScore;

    private int awayScore;

    private UUID refereeId;

    private boolean processed;

    public Match(UUID id, UUID homeTeamId, UUID awayTeamId, MatchStatus status, LocalDateTime date, int homeScore, int awayScore, UUID refereeId) {
        this.id = id;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.status = status;
        this.date = date;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.refereeId = refereeId;
    }
}
