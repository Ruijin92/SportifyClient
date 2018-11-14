package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class TournamentDTO implements Serializable {

    public TournamentDTO() { }

    private static final long serialVersionUID = 111111111267757690L;

    private String id;

    private String name;
    private String leagueName;
    private String sportsName;
    private LeagueDTO league;
    private List<EncounterDTO> encounters;
    private List<ParticipantDTO> teams;
    private ResponseMessageDTO response;
}
