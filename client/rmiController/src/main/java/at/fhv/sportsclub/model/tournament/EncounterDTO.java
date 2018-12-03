package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public @Data class EncounterDTO implements Serializable {

    public EncounterDTO() { }

    private static final long serialVersionUID = 1111111098267757692L; // changed 26.11

    private String id;

    private LocalDate date;
    private int time;
    private ParticipantDTO homeTeam;
    private ParticipantDTO guestTeam;
    private int homePoints;
    private int guestPoints;
    private ResponseMessageDTO response;

    private ModificationType modificationType;
}
