package at.fhv.team2.teams.ViewModels;

import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.team2.member.PersonViewModel;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * Created by Uray Örnek on 11/19/2018.
 */
public class TeamViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private List<PersonDTO> members;
    private List<PersonDTO> trainers;
    private SimpleStringProperty league;
    private SimpleStringProperty type;

    private String loadedParticipantId;
    private ModificationType modificationType;

    public TeamViewModel(String id, String name, List<PersonDTO> members, List<PersonDTO> trainers, String league, String type, ModificationType modificationType) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.members = members;
        this.trainers = trainers;
        this.league = new SimpleStringProperty(league);
        this.type = new SimpleStringProperty(type);
        this.modificationType = modificationType;
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public String getLoadedParticipantId() {
        return loadedParticipantId;
    }

    public void setLoadedParticipantId(String loadedParticipantId) {
        this.loadedParticipantId = loadedParticipantId;
    }

    public ModificationType getModificationType() {
        return modificationType;
    }

    public void setModificationType(ModificationType modificationType) {
        this.modificationType = modificationType;
    }
}
