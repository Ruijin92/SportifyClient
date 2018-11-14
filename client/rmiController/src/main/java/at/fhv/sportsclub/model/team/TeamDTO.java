package at.fhv.sportsclub.model.team;


import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class TeamDTO implements Serializable {

    public TeamDTO() { }

    private static final long serialVersionUID = 1111115098267757690L;

    private String id;

    private String name;
    private List<PersonDTO> members;
    private List<PersonDTO> trainers;
    private LeagueDTO league;
    private String type;
    private ResponseMessageDTO response;
}
