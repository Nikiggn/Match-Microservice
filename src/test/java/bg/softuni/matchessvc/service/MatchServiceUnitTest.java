package bg.softuni.matchessvc.service;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.model.MatchStatus;
import bg.softuni.matchessvc.repository.MatchRepository;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.dto.MatchForProcessing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceUnitTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void shouldCreateMatchSuccessfully() {
        MatchCreation request = new MatchCreation();
        request.setHomeTeamId(UUID.randomUUID());
        request.setAwayTeamId(UUID.randomUUID());
        request.setStatus(MatchStatus.UPCOMING);
        request.setDate(LocalDateTime.of(2025, 1, 1, 0, 0));

        Match match = new Match(null, request.getHomeTeamId(), request.getAwayTeamId(),
                request.getStatus(), request.getDate(), 0, 0, UUID.randomUUID());

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        Match savedMatch = matchService.createMatch(request);

        assertNotNull(savedMatch);
        assertEquals(request.getHomeTeamId(), savedMatch.getHomeTeamId());
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNull() {
        assertThrows(NullPointerException.class, () -> matchService.createMatch(null));
    }

    @Test
    void whenGettingAllMatches_thenReturnsListOfMatches() {
        List<Match> matches = List.of(
                new Match(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), MatchStatus.COMPLETED, LocalDateTime.now(), 0,0,UUID.randomUUID()),
                new Match(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), MatchStatus.COMPLETED, LocalDateTime.now(), 0,0,UUID.randomUUID())
        );

        when(matchRepository.findAll()).thenReturn(matches);

        List<Match> result = matchService.getAllMatches();

        assertEquals(matches.size(), result.size());
        assertEquals(matches, result);

        verify(matchRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnMatchesWhenClubHasMatches() {
        UUID clubId = UUID.randomUUID();
        List<Match> matches = List.of(
                new Match(UUID.randomUUID(), clubId, UUID.randomUUID(), MatchStatus.COMPLETED, LocalDateTime.now(), 1, 2, UUID.randomUUID()),
                new Match(UUID.randomUUID(), UUID.randomUUID(), clubId, MatchStatus.UPCOMING, LocalDateTime.now().plusDays(2), 0, 0, UUID.randomUUID())
        );

         when(matchRepository.findByHomeTeamIdOrAwayTeamId(clubId, clubId)).thenReturn(matches);

         List<MatchCreation> result = matchService.getMatchesByClubId(clubId);
         assertEquals(2, result.size()); // Ensure the number of matches returned is correct
        assertEquals(clubId, result.get(0).getHomeTeamId()); // Ensure the correct team ID is used
        assertEquals(clubId, result.get(1).getAwayTeamId());
    }

    @Test
    void shouldReturnEmptyListWhenClubHasNoMatches() {
        UUID clubId = UUID.randomUUID();

         when(matchRepository.findByHomeTeamIdOrAwayTeamId(clubId, clubId)).thenReturn(Collections.emptyList());

         List<MatchCreation> result = matchService.getMatchesByClubId(clubId);

         assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateMatch_Success() {
        // Arrange
        UUID matchId = UUID.randomUUID();
        Match existingMatch = new Match();
        existingMatch.setId(matchId);
        existingMatch.setProcessed(false);

        MatchForProcessing updateRequest = new MatchForProcessing();
        updateRequest.setProcessed(true);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(existingMatch));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Match updatedMatch = matchService.updateMatch(matchId, updateRequest);

        // Assert
        assertNotNull(updatedMatch);
        assertTrue(updatedMatch.isProcessed());  // Ensuring the processed flag was updated
        verify(matchRepository).findById(matchId);
        verify(matchRepository).save(existingMatch);
    }


}
