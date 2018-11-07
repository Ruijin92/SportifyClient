package at.fhv.sportsclub.model.dept;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@AllArgsConstructor
public @Data class LeagueDTO implements Serializable {

    public LeagueDTO() {}

    private String id;
    private String name;
}
