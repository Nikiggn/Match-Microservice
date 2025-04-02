package bg.softuni.matchessvc.service;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.repository.MatchRepository;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.dto.MatchForProcessing;
import bg.softuni.matchessvc.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        match.setProcessed(false);

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

    public List<MatchCreation> getMatchesByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("status").ascending());

        return matchRepository.findAll(pageable)
                .getContent()
                .stream()
                .sorted(Comparator.comparing(Match::getStatus))
                .map(DtoMapper::fromMatchCreation)
                .toList();
    }

    public List<Match> getUnprocessedMatches() {
        return matchRepository.findByProcessedFalse();  // Return unprocessed matches
    }

    public Match updateMatch(UUID id, MatchForProcessing match) {
        // Find match by ID and update its status
        Match existingMatch = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
        existingMatch.setProcessed(match.isProcessed());  // Update processed status
        return matchRepository.save(existingMatch);  // Save updated match
    }
}

