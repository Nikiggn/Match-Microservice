package bg.softuni.matchessvc.service;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.repository.MatchRepository;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.dto.MatchSummaryResponse;
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
                request.getAwayScore());

        return matchRepository.save(match);
    }

//    public MatchSummaryResponse getMatch(UUID matchId) {
//        Match match = matchRepository.findById(matchId)
//                .orElseThrow(() -> new RuntimeException("Match not found"));
//
//        return new MatchSummaryResponse(
//                match.getId(),
//                match.getHomeTeamId(),
//                match.getAwayTeamId(),
//                match.getDate(),
//                match.getStatus(),
//                match.getHomeScore(),
//                match.getAwayScore()
//        );
//    }

//    public List<MatchSummaryResponse> getAllMatchesByClub(Long teamId) {
//
//    }
}
