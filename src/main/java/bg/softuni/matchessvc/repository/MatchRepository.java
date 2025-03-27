package bg.softuni.matchessvc.repository;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {
    List<Match> findByHomeTeamIdOrAwayTeamId(UUID homeTeamId, UUID awayTeamId);
 }
