package at.fhv.team2.teams;

import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.team2.member.PersonViewModel;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * Created by Uray Örnek on 11/19/2018.
 */
public class TeamViewModel {

    private SimpleStringProperty name;
    private List<PersonDTO> members;
    private List<PersonDTO> trainers;
    private SimpleStringProperty league;
    private SimpleStringProperty type;

    public TeamViewModel(String name, List<PersonDTO> members, List<PersonDTO> trainers, String league, String type) {
        this.name = new SimpleStringProperty(name);
        this.members = members;
        this.trainers = trainers;
        this.league = new SimpleStringProperty(league);
        this.type = new SimpleStringProperty(type);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public List<PersonDTO> getMembers() {
        return members;
    }

    public void setMembers(List<PersonDTO> members) {
        this.members = members;
    }

    public List<PersonDTO> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<PersonDTO> trainers) {
        this.trainers = trainers;
    }

    public String getLeague() {
        return league.get();
    }

    public SimpleStringProperty leagueProperty() {
        return league;
    }

    public void setLeague(String league) {
        this.league.set(league);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
