package at.fhv.team2.wettkampf.ViewModels;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class CompetitionViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty leagueName;
    private SimpleStringProperty sportsName;
    private SimpleStringProperty league;
    private SimpleStringProperty date;

    private List<EncounterViewModel> encounters;
    private List<ParticipantViewModel> participants;

    public CompetitionViewModel(String id, String name, String leagueName, String sportsName, String league,
                                List<EncounterViewModel> encounters, List<ParticipantViewModel> participants) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.leagueName = new SimpleStringProperty(leagueName);
        this.sportsName = new SimpleStringProperty(sportsName);
        this.league = new SimpleStringProperty(league);
        this.encounters = encounters;
        this.participants = participants;
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLeagueName() {
        return leagueName.get();
    }

    public SimpleStringProperty leagueNameProperty() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName.set(leagueName);
    }

    public String getSportsName() {
        return sportsName.get();
    }

    public SimpleStringProperty sportsNameProperty() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName.set(sportsName);
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

    public List<EncounterViewModel> getEncounters() {
        return encounters;
    }

    public void setEncounters(List<EncounterViewModel> encounters) {
        this.encounters = encounters;
    }

    public List<ParticipantViewModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantViewModel> participants) {
        this.participants = participants;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    //endregion
}
