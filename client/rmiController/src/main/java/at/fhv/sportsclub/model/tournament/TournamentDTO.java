package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public @Data class TournamentDTO implements Serializable {

    public TournamentDTO() {}

    private static final long serialVersionUID = 111111111267757691L; // changed 20.11.2018

    private String id;

    private String name;
    private String league;
    private String sport;
    private String leagueName;
    private String sportsName;
    private LocalDate date;
    private List<EncounterDTO> encounters;
    private List<ParticipantDTO> teams;
    private ResponseMessageDTO response;
    private ModificationType modificationType;
}
