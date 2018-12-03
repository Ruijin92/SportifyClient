package at.fhv.team2.teams;

import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.team2.PageProvider;
import at.fhv.team2.teams.ViewModels.TeamViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class AllTeams extends HBox implements Initializable {

    public TableView table;
    public Button teamSquadButton;

    private ITournamentController tournamentController;

    public AllTeams() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AllTeams.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TODO: Anhand der User ID alle bevorstehenden Wettkämpfe laden
    }

    public void clickItem(MouseEvent event) {
        teamSquadButton.setDisable(false);
    }

    private void addTeams() {

    }

    /**
     * get's triggerd if MannschaftsKader is pressed
     * @param event
     */
    public void teamSquad(ActionEvent event) {

        //TODO: sind ja eigendlich Wettlkämpfer die er da sieht -> muss die richtige mannschaft dazu gesucht werden
        //TODO: oder es zeigt seine mannschaften an die als zusatz haben in welchen Wettkampf ka?
        //TODO: TeamViewModel ist das alte Model das neue ist Participant
        TeamViewModel selectedItem =  (TeamViewModel)table.getSelectionModel().getSelectedItem();
        PageProvider.getPageProvider().switchTeamSquad(selectedItem);
    }


    private ArrayList<TeamDTO> addTestData() {

        ArrayList<TeamDTO> teamEntries = new ArrayList<>();

        LeagueDTO l1 = new LeagueDTO();
        LeagueDTO l2 = new LeagueDTO();

        l1.setName("A-Liga");
        l2.setName("TennisLiga");

        PersonDTO p1 = new PersonDTO();
        PersonDTO p2 = new PersonDTO();
        p1.setFirstName("Uray");
        p1.setLastName("Örnek");
        p2.setFirstName("Peter");
        p2.setLastName("Fister");


        TeamDTO t1 = new TeamDTO();
        TeamDTO t2 = new TeamDTO();
        t1.setName("FC-Dornbirn");
        t1.setLeague(l1);
        t1.setType("intern");
        t1.setMembers(Collections.singletonList(p1));
        t1.setTrainers(Collections.singletonList(p1));
        t2.setName("FC-Feldkirch");
        t2.setLeague(l2);
        t2.setType("extern");
        t1.setMembers(Collections.singletonList(p2));
        t2.setTrainers(Collections.singletonList(p2));

        teamEntries.add(t1);
        teamEntries.add(t2);
        return teamEntries;
    }

}
