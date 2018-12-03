package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.PageProvider;
import at.fhv.team2.member.PersonViewModel;
import at.fhv.team2.roles.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class AllCompetition extends HBox implements Initializable {

    public HBox hBox;

    public Button changeButton;
    public Button newButton;
    public Button resultButton;
    public Button searchButton;

    public ListView listCompetitions;
    private List<CompetitionViewModel> tournaments = new ArrayList<>();
    private ObservableList<CompetitionViewModel> competitionTableList;

    private CompetitionViewModel tournamentDetails;
    private ITournamentController tournamentControllerInstance;

    private PageProvider pageProvider = PageProvider.getPageProvider();

    public AllCompetition() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AllCompetitions.fxml"));
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

        newButton.setVisible(Permission.getPermission().createCompetitionPermission());
        resultButton.setVisible(Permission.getPermission().createCompetitionPermission());
        changeButton.setVisible(Permission.getPermission().createCompetitionPermission());

        changeButton.setDisable(true);

        //FIXME: this.tournamentControllerInstance = DataProvider.getTournamentControllerInstance();
        ArrayList<TournamentDTO> tournamentEntries = addTestData(); //FIXME: muss auf null wieder geändert werden
        /*
        try {
            tournamentEntries = tournamentControllerInstance.getAllEntries(DataProvider.getSession()).getContents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        */

        for (TournamentDTO tournament : tournamentEntries) {
            tournaments.add(new CompetitionViewModel(tournament.getId(), tournament.getName(), null, null, null, null, null));
        }
        addCompetitionsToList(tournaments);

        listCompetitions.setCellFactory(lv -> new ListCell<CompetitionViewModel>() {
            @Override
            public void updateItem(CompetitionViewModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    public void changeCompetition(ActionEvent event) {

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

        }
    }

    public void createCompetition(ActionEvent event) {
        pageProvider.switchNewComp();
    }

    public void enterResult(ActionEvent event) {

    }

    private void addCompetitionsToList(List<CompetitionViewModel> competitions) {
        competitionTableList = FXCollections.observableArrayList(competitions);
        listCompetitions.setItems(competitionTableList);
    }

    public void clickItem(MouseEvent event){

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            changeButton.setDisable(false);
        } else {
            changeButton.setVisible(true);
        }
    }

    //TODO: mit Server verbinden und Daten auf Screen binden
    public void showDetailInformations() {
       /* //CompetitionViewModel pr = (CompetitionViewModel) table.getSelectionModel().getSelectedItem();
        TournamentDTO tournamentEntryDetails = new TournamentDTO();

        try {
            tournamentEntryDetails = tournamentControllerInstance.getEntryDetails(DataProvider.getSession(), pr.getId());
            if (tournamentEntryDetails.getResponse() != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Loading failed");
                alert.setContentText("Loading of tournament failed");
                alert.showAndWait();
                return;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        List<ParticipantViewModel> teams = null;
        for (ParticipantDTO participant: tournamentEntryDetails.getTeams()) {
            List<PersonViewModel> participants = createParticipants(participant.getParticipants());
            teams.add(new ParticipantViewModel(participant.getId(), participant.getTeam(), participant.getTeamName(), participants));
        }
        List<EncounterViewModel> encounters = null;
        for (EncounterDTO encounterEntry: tournamentEntryDetails.getEncounters()) {
            List<PersonViewModel> homeTeamPersons = createParticipants(encounterEntry.getHomeTeam().getParticipants());
            List<PersonViewModel> guestTeamPersons = createParticipants(encounterEntry.getGuestTeam().getParticipants());
            ParticipantViewModel homeTeam = new ParticipantViewModel(encounterEntry.getHomeTeam().getId(), encounterEntry.getHomeTeam().getTeam(), encounterEntry.getHomeTeam().getTeamName(), homeTeamPersons);
            ParticipantViewModel guestTeam = new ParticipantViewModel(encounterEntry.getGuestTeam().getId(), encounterEntry.getGuestTeam().getTeam(), encounterEntry.getGuestTeam().getTeamName(), guestTeamPersons);
            encounters.add(new EncounterViewModel(encounterEntry.getId(), encounterEntry.getDate().toString(), encounterEntry.getTime().toString(), homeTeam, guestTeam, encounterEntry.getHomePoints(), encounterEntry.getGuestPoints()));
        }
        tournamentDetails = new CompetitionViewModel(tournamentEntryDetails.getId(), tournamentEntryDetails.getName(), tournamentEntryDetails.getLeagueName(), tournamentEntryDetails.getSportsName(), tournamentEntryDetails.getLeague(), encounters, teams);
   */
    }

    //TODO: brauchen wir das noch
    private List<PersonViewModel> createParticipants(List<PersonDTO> participantsDTO) {
        List<PersonViewModel> participants = new LinkedList<>();
        for (PersonDTO personEntry : participantsDTO) {
            List<String> sports = new LinkedList<>();
            for (SportDTO sportEntry : personEntry.getSports()) {
                sports.add(sportEntry.getName());
            }
            participants.add(new PersonViewModel(personEntry.getId(), personEntry.getFirstName(), personEntry.getLastName(),
                    personEntry.getAddress().getCity(), personEntry.getAddress().getStreet(),
                    personEntry.getAddress().getZipCode(), personEntry.getContact().getPhoneNumber(), sports));
        }
        return participants;
    }

    //FIXME: wird nur zu test zwecken benutzt
    private ArrayList<TournamentDTO> addTestData() {
        ArrayList<TournamentDTO> list = new ArrayList<>();
        list.add(new TournamentDTO("3", "FC-Dornbirn", null, null, null, null, null, null,null));
        return list;
    }
}
