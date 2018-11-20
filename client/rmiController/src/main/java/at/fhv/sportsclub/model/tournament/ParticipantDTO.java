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

    private static final long serialVersionUID = 111111198267757691L; // changed 20.11.2018

    private String id;

    private String team;    // custom resolver
    private String teamName;
    private List<PersonDTO> participants;
    private ResponseMessageDTO response;
}
