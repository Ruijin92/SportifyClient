package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.member.PersonViewModel;
import at.fhv.team2.roles.Permission;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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

    private ListView listCompetitions;

    private List<CompetitionViewModel> tournaments;

    private ITournamentController tournamentControllerInstance;

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
        addCompetitions();

        this.tournamentControllerInstance = DataProvider.getTournamentControllerInstance();

        ArrayList<TournamentDTO> tournamentEntries = null;

        /*try {
            tournamentEntries = tournamentControllerInstance.getAllEntries(DataProvider.getSession()).getContents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/

        tournaments = new ArrayList<>();

        for (TournamentDTO tournament: tournamentEntries) {
            List<ParticipantViewModel> teams = null;
            for (ParticipantDTO participant: tournament.getTeams()) {
                List<PersonViewModel> participants = createParticipants(participant.getParticipants());
                teams.add(new ParticipantViewModel(participant.getId(), participant.getTeam(), participant.getTeamName(), participants));
            }
            List<EncounterViewModel> encounters = null;
            for (EncounterDTO encounterEntry: tournament.getEncounters()) {
                List<PersonViewModel> homeTeamPersons = createParticipants(encounterEntry.getHomeTeam().getParticipants());
                List<PersonViewModel> guestTeamPersons = createParticipants(encounterEntry.getGuestTeam().getParticipants());
                ParticipantViewModel homeTeam = new ParticipantViewModel(encounterEntry.getHomeTeam().getId(), encounterEntry.getHomeTeam().getTeam(), encounterEntry.getHomeTeam().getTeamName(), homeTeamPersons);
                ParticipantViewModel guestTeam = new ParticipantViewModel(encounterEntry.getGuestTeam().getId(), encounterEntry.getGuestTeam().getTeam(), encounterEntry.getGuestTeam().getTeamName(), guestTeamPersons);
                encounters.add(new EncounterViewModel(encounterEntry.getId(), encounterEntry.getDate().toString(), encounterEntry.getTime().toString(), homeTeam, guestTeam, encounterEntry.getHomePoints(), encounterEntry.getGuestPoints()));
            }
            tournaments.add(new CompetitionViewModel(tournament.getId(), tournament.getName(), tournament.getLeagueName(), tournament.getSportsName(), tournament.getLeague(), encounters, teams));
        }
    }

    public void changeCompetition(ActionEvent event) {

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();

        if(selectedItem != null){

        }
    }

    public void createCompetition(ActionEvent event) {

    }

    public void enterResult(ActionEvent event) {

    }

    private void addCompetitions() {

    }

    private List<PersonViewModel> createParticipants(List<PersonDTO> participantsDTO) {
        List<PersonViewModel> participants = new LinkedList<>();
        for (PersonDTO personEntry: participantsDTO) {
            List<String> sports = new LinkedList<>();
            for (SportDTO sportEntry: personEntry.getSports()) {
                sports.add(sportEntry.getName());
            }
            participants.add(new PersonViewModel(personEntry.getId(), personEntry.getFirstName(), personEntry.getLastName(),
                    personEntry.getAddress().getCity(),personEntry.getAddress().getStreet(),
                    personEntry.getAddress().getZipCode(), personEntry.getContact().getPhoneNumber(), sports));
        }
        return participants;
    }
}
