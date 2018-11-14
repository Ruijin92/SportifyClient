package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class ResultDTO implements Serializable {

    public ResultDTO() { }

    private static final long serialVersionUID = 111111118267757690L;

    private String id;

    private TeamDTO team;
    private int points;
    private ResponseMessageDTO response;
}
