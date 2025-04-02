package bg.softuni.matchessvc.web;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.service.MatchService;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.dto.MatchForProcessing;
import bg.softuni.matchessvc.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("/page")
    public List<MatchCreation> getMatches(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int pageSize) {
        return matchService.getMatchesByPage(page, pageSize);
    }

    @GetMapping("/unprocessed")
    public List<MatchForProcessing> getUnprocessedMatches() {
        List<Match> matches = matchService.getUnprocessedMatches();
        return matches.stream()
                .map(DtoMapper::fromMatchForProcessing)
                .collect(Collectors.toList());  // Convert List<Match> to List<MatchDTO>
    }

    @PutMapping("/{id}")
    public MatchForProcessing updateMatchStatus(@PathVariable UUID id, @RequestBody MatchForProcessing matchDTO) {
        Match match = matchService.updateMatch(id, matchDTO);  // Update logic
        return DtoMapper.fromMatchForProcessing(match);  // Return updated match as DTO
    }


}
