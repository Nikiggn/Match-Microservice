package bg.softuni.matchessvc.web;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.service.MatchService;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }


    @PostMapping("/new")
    public ResponseEntity<Void> createMatch(@RequestBody MatchCreation request) {
        matchService.createMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MatchCreation>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        List<MatchCreation> list  = matches.stream().map(DtoMapper::fromMatchCreation).toList();

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }

    @GetMapping("/club")
    public ResponseEntity<List<MatchCreation>> getMatchesByClubId(@RequestParam UUID clubId) {
        List<MatchCreation> matches = matchService.getMatchesByClubId(clubId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(matches);
    }
}
