package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class ParticipantDTO implements Serializable {

    public ParticipantDTO() { }

    private static final long serialVersionUID = 111111198267757690L;

    private String id;

    private TeamDTO team;
    private String teamName;
    private List<PersonDTO> participants;
    private ResponseMessageDTO response;
}
