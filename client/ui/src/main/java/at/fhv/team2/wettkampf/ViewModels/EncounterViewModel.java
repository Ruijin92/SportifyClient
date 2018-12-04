package at.fhv.team2.wettkampf.ViewModels;

import at.fhv.sportsclub.model.common.ModificationType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EncounterViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private ParticipantViewModel homeTeam;
    private ParticipantViewModel guestTeam;

    private SimpleIntegerProperty homePoints;
    private SimpleIntegerProperty guestPoints;

    private ModificationType modificationType;

    public EncounterViewModel(String id, String date, String time, ParticipantViewModel homeTeam, ParticipantViewModel guestTeam,
                              int homePoints, int guestPoints, ModificationType modificationType) {
        this.id = new SimpleStringProperty(id);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.homeTeam = homeTeam;
        this.guestTeam = guestTeam;
        this.homePoints = new SimpleIntegerProperty(homePoints);
        this.guestPoints = new SimpleIntegerProperty(guestPoints);
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

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getHomeTeam() {
        return homeTeam.getTeamName();
    }

    public void setHomeTeam(ParticipantViewModel homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getGuestTeam() {
        return guestTeam.getTeamName();
    }

    public void setGuestTeam(ParticipantViewModel guestTeam) {
        this.guestTeam = guestTeam;
    }

    public int getHomePoints() {
        return homePoints.get();
    }

    public SimpleIntegerProperty homePointsProperty() {
        return homePoints;
    }

    public void setHomePoints(int homePoints) {
        this.homePoints.set(homePoints);
    }

    public int getGuestPoints() {
        return guestPoints.get();
    }

    public SimpleIntegerProperty guestPointsProperty() {
        return guestPoints;
    }

    public void setGuestPoints(int guestPoints) {
        this.guestPoints.set(guestPoints);
    }

    public ParticipantViewModel getHomeTeamModel() { return homeTeam; }

    public ParticipantViewModel getGuestTeamModel() { return guestTeam; }

    public ModificationType getModificationType() {
        return modificationType;
    }

    public void setModificationType(ModificationType modificationType) {
        this.modificationType = modificationType;
    }

    //endregion
}
