package bg.softuni.matchessvc.controller;

import bg.softuni.matchessvc.model.Match;
import bg.softuni.matchessvc.model.MatchStatus;
import bg.softuni.matchessvc.service.MatchService;
import bg.softuni.matchessvc.web.MatchController;
import bg.softuni.matchessvc.web.dto.MatchCreation;
import bg.softuni.matchessvc.web.mapper.DtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
public class MatchControllerApiTest {
    @MockitoBean
    private MatchService matchService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser// това е за симулиране на Aunth user
    void shouldCreateMatchSuccessfully() throws Exception {
         MatchCreation matchCreation = new MatchCreation();
        matchCreation.setHomeTeamId(UUID.randomUUID());
        matchCreation.setAwayTeamId(UUID.randomUUID());
        matchCreation.setDate(LocalDateTime.of(2025, 1, 1, 12, 0));
        matchCreation.setStatus(MatchStatus.UPCOMING);
        matchCreation.setRefereeId(UUID.randomUUID());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // DTO -> JSON
        String jsonRequest = objectMapper.writeValueAsString(matchCreation);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/matches/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .with(csrf()))
                        .andExpect(status().isCreated());

        verify(matchService, times(1)).createMatch(any(MatchCreation.class));
    }



    @Test
    void shouldReturnEmptyMatchListWhenNoMatchesFoundForClub() throws Exception {
        UUID clubId = UUID.randomUUID();
        when(matchService.getMatchesByClubId(clubId)).thenReturn(List.of());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/matches/club")
                .param("clubId", clubId.toString()) // Correctly pass UUID as request parameter
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()); // CSRF token for security

        // Assert
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(matchService, times(1)).getMatchesByClubId(clubId);
    }


    @Test
    void shouldReturnMatchListWhenMatchesExistForClub() throws Exception {
        UUID clubId = UUID.randomUUID();
        List<MatchCreation> matches = List.of(
                new MatchCreation(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), MatchStatus.COMPLETED,0,0, UUID.randomUUID()),
                new MatchCreation(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), MatchStatus.COMPLETED,0,0, UUID.randomUUID())
        );

        when(matchService.getMatchesByClubId(clubId)).thenReturn(matches);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/matches/club")
                .param("clubId", clubId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(matches.size()))
                .andExpect(jsonPath("$[0].homeTeamId").value(matches.get(0).getHomeTeamId().toString()))
                .andExpect(jsonPath("$[1].status").value(matches.get(1).getStatus().toString()));

        verify(matchService, times(1)).getMatchesByClubId(clubId);
    }

    @Test
    void shouldReturnBadRequestWhenClubIdIsMissing() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/matches/club") // няма param
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }


    @Test
    void ddv() throws Exception {
        List<Match> matches = List.of(
                new Match(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), MatchStatus.COMPLETED, LocalDateTime.now(), 0,0,UUID.randomUUID()),
                new Match(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), MatchStatus.COMPLETED, LocalDateTime.now(), 0,0,UUID.randomUUID())
                );

        List<MatchCreation> matchCreations = matches.stream()
                .map(DtoMapper::fromMatchCreation)
                .toList();

        when(matchService.getAllMatches()).thenReturn(matches);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(matches.size()))
                .andExpect(jsonPath("$[0].homeTeamId").value(matchCreations.get(0).getHomeTeamId().toString()))
                .andExpect(jsonPath("$[1].status").value(matchCreations.get(1).getStatus().toString()));

        verify(matchService, times(1)).getAllMatches();
    }


}
