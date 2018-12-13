package at.fhv.team2.wettkampf.ViewModels;

import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.team2.member.PersonViewModel;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ParticipantViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty team;
    private SimpleStringProperty teamName;

    private List<PersonViewModel> participants;

    private ModificationType modificationType;

    public ParticipantViewModel(String id, String team, String teamName, List<PersonViewModel> participants, ModificationType modificationType) {
        this.id = new SimpleStringProperty(id);
        this.team = new SimpleStringProperty(team);
        this.teamName = new SimpleStringProperty(teamName);
        this.participants = participants;
        this.modificationType = modificationType;
    }

    //region Getter and Setter
    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTeam() {
        return team.get();
    }

    public SimpleStringProperty teamProperty() {
        return team;
    }

    public void setTeam(String team) {
        this.team.set(team);
    }

    public String getTeamName() {
        return teamName.get();
    }

    public SimpleStringProperty teamNameProperty() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public List<PersonViewModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PersonViewModel> participants) {
        this.participants = participants;
    }

    public ModificationType getModificationType() {
        return modificationType;
    }

    public void setModificationType(ModificationType modificationType) {
        this.modificationType = modificationType;
    }

    //endregion
}
