package bg.softuni.matchessvc.service;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.repository.MatchRepository;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match createMatch(MatchCreation request) {


        Match match = new Match(null,
                request.getHomeTeamId(),
                request.getAwayTeamId(),
                request.getStatus(),
                request.getDate(),
                request.getHomeScore(),
                request.getAwayScore(),
                request.getRefereeId());

        return matchRepository.save(match);
    }

    public List<MatchCreation> getMatchesByClubId(UUID clubId) {
        List<Match> matches = matchRepository.findByHomeTeamIdOrAwayTeamId(clubId, clubId);

        return matches.stream().map(match -> new MatchCreation(
                match.getHomeTeamId(),
                match.getAwayTeamId(),
                match.getDate(),
                match.getStatus(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getRefereeId()
        )).toList();
    }

}
