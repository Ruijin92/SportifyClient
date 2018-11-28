package at.fhv.sportsclub.model.dept;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class SportDTO implements Serializable {

    public SportDTO() {}

    private static final long serialVersionUID = 1119685098267757690L;

    private String id;
    private String name;
    private List<LeagueDTO> leagues;
    private ResponseMessageDTO response;
}
