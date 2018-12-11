package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/*
      Created: 06.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class SquadMemberDTO implements Serializable {

    private static final long serialVersionUID = 111111198212757692L; // changed 26.11.2018

    public SquadMemberDTO() {}

    private PersonDTO member;
    private boolean participating;
    private ResponseMessageDTO response;
    private boolean informed;
}
