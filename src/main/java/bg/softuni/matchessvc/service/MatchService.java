package bg.softuni.matchessvc.service;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.repository.MatchRepository;
import bg.softuni.matchessvc.web.dto.MatchCreations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Match createMatch(MatchCreations request) {
        Match match = new Match(null,
                request.getHomeTeamId(),
                request.getAwayTeamId(),
                request.getStatus(),
                request.getDate(),
                request.getHomeScore(),
                request.getAwayScore());

        return matchRepository.save(match);
    }
}
