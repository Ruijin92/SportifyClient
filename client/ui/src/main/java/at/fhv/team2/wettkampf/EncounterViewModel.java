package at.fhv.team2.wettkampf;

import javafx.beans.property.SimpleStringProperty;

public class EncounterViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private ParticipantViewModel homeTeam;
    private ParticipantViewModel guestTeam;

    private SimpleStringProperty homePoints;
    private SimpleStringProperty guestPoints;

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

    public ParticipantViewModel getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(ParticipantViewModel homeTeam) {
        this.homeTeam = homeTeam;
    }

    public ParticipantViewModel getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(ParticipantViewModel guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getHomePoints() {
        return homePoints.get();
    }

    public SimpleStringProperty homePointsProperty() {
        return homePoints;
    }

    public void setHomePoints(String homePoints) {
        this.homePoints.set(homePoints);
    }

    public String getGuestPoints() {
        return guestPoints.get();
    }

    public SimpleStringProperty guestPointsProperty() {
        return guestPoints;
    }

    public void setGuestPoints(String guestPoints) {
        this.guestPoints.set(guestPoints);
    }
    //endregion
}
