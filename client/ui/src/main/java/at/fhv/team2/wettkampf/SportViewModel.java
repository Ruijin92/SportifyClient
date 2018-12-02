package at.fhv.team2.wettkampf;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class SportViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private ArrayList<LeagueViewModel> leagues;

    public SportViewModel(String id, String name, ArrayList<LeagueViewModel> leagues) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.leagues = leagues;
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

    public ArrayList<LeagueViewModel> getLeagues() {
        return leagues;
    }

    public void setLeagues(ArrayList<LeagueViewModel> leagues) {
        this.leagues = leagues;
    }
}
