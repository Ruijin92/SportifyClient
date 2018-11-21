package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public @Data class EncounterDTO implements Serializable {

    public EncounterDTO() { }

    private static final long serialVersionUID = 1111111098267757691L;

    private String id;

    private LocalDate date;
    private LocalTime time;
    private ParticipantDTO homeTeam;
    private ParticipantDTO guestTeam;
    private int homePoints;
    private int guestPoints;
    private ResponseMessageDTO response;
}
