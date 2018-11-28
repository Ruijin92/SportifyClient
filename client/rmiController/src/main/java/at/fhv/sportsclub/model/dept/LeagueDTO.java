package at.fhv.sportsclub.model.dept;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@AllArgsConstructor
public @Data class LeagueDTO implements Serializable {

    public LeagueDTO() {}

    private static final long serialVersionUID = 1109685098267757690L;

    private String id;
    private String name;
}
