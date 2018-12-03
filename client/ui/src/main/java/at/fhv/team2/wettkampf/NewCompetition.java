package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.teams.ViewModels.TeamViewModel;
import at.fhv.team2.wettkampf.ViewModels.LeagueViewModel;
import at.fhv.team2.wettkampf.ViewModels.SportViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewCompetition extends HBox implements Initializable {

    public HBox hBox;
    public ComboBox sportsCombo;
    public ComboBox leagueCombo;
    public DatePicker datePick;
    public ListSelectionView listView;
    public TextField teamName;
    public TextField tournamentName;

    private ObservableList<TeamViewModel> teams;

    private ArrayList<TeamViewModel> teamViewModels = new ArrayList<>();
    private ArrayList<SportViewModel> allSports;
    private ITeamController teamControllerInstance;
    private IDepartmentController departmentController;
    private ITournamentController tournamentController;

    public NewCompetition() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NewCompetition.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listView.setSourceHeader(new Label("Verfügbare Mannschaften"));
        listView.setTargetHeader(new Label("Ausgewählte Mannschaften"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leagueCombo.setDisable(true);

        this.departmentController = DataProvider.getDepartmentControllerInstance();
        this.teamControllerInstance = DataProvider.getTeamControllerInstance();

       addSports();

        sportsCombo.valueProperty().addListener(event -> {
            leagueCombo.setDisable(false);
            addLeague();
            listView.getSourceItems().clear();
            listView.getTargetItems().clear();
            /*try {
                addTeamsBySportToAvailableList();
                listView.getSourceItems().add(teams);
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
        });

        leagueCombo.valueProperty().addListener(event -> {
            listView.getTargetItems().clear();
            listView.getSourceItems().clear();
            try {
                addTeamsByLeagueToAvailableList();
                listView.getSourceItems().add(teams);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public void addExternTeam(ActionEvent event) {
        listView.getSourceItems().add(new TeamViewModel(null, teamName.getText(), null, null, null, null));
    }

    public void saveComp(ActionEvent event) throws RemoteException {
        ArrayList<TeamViewModel> teams = new ArrayList<>();
        //teams.addAll(listView.getTargetItems());
        ObservableList targetItems = listView.getTargetItems();
        List<TeamViewModel> list = (List<TeamViewModel>) targetItems.stream().collect(Collectors.toList());


        ArrayList<ParticipantDTO> participantTeams = new ArrayList<>();

        for (TeamViewModel team: teams) {
            participantTeams.add(new ParticipantDTO(null, team.getId(), null, null, null, ModificationType.MODIFIED));
        }

        LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();
        TournamentDTO tournament = new TournamentDTO(null, tournamentName.getText(), selectedLeague.getId(), null, null, null, participantTeams, null, ModificationType.MODIFIED);

        this.tournamentController = DataProvider.getTournamentControllerInstance();
        //savedTournament --> Um zu überprüfen ob alles erfolgreich in die Datenbank gespeichert wurde.
        TournamentDTO savedTournament = this.tournamentController.saveOrUpdateEntry(DataProvider.getSession(), tournament);

        //Encoutner
        PageProvider.getPageProvider().switchEncounter(tournament);
    }

    private void addSports() {

        ArrayList<SportDTO> sports = null;

        try {
            sports = this.departmentController.getAllSportEntriesFull(DataProvider.getSession()).getContents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        this.allSports = new ArrayList<>();

        for (SportDTO sport : sports) {
            ArrayList<LeagueViewModel> leagues = new ArrayList<>();
            for (LeagueDTO league : sport.getLeagues()) {
                leagues.add(new LeagueViewModel(league.getId(), league.getName()));
            }
            this.allSports.add(new SportViewModel(sport.getId(), sport.getName(), leagues));
        }

        ObservableList<SportViewModel> allItems = FXCollections.observableArrayList(this.allSports);
        sportsCombo.setItems(allItems);
        sportsCombo.setConverter(new StringConverter<SportViewModel>() {
            @Override
            public String toString(SportViewModel object) {
                return object.getName();
            }

            @Override
            public SportViewModel fromString(String string) {
                return null;
            }
        });
    }

    private void addLeague() {

        SportViewModel sport = (SportViewModel) sportsCombo.getSelectionModel().getSelectedItem();
        ArrayList<LeagueViewModel> leagues = sport.getLeagues();

        ObservableList<LeagueViewModel> allItems = FXCollections.observableArrayList(leagues);
        leagueCombo.setItems(allItems);
        leagueCombo.setConverter(new StringConverter<LeagueViewModel>() {
            @Override
            public String toString(LeagueViewModel object) {
                return object.getName();
            }

            @Override
            public LeagueViewModel fromString(String string) {
                return null;
            }
        });
    }

    private void addTeamsByLeagueToAvailableList() throws RemoteException {
        //"5c04554448a886e2bbad15c2" --> Test LeagueID bei welcher ein Team hinterlegt ist.
        LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();

        ArrayList<TeamDTO> teams = this.teamControllerInstance.getByLeague(DataProvider.getSession(),"5c04e93a9b1ba6a0795fc7a1").getContents();

        teamViewModels.clear();
        if (teams != null) {
            for (TeamDTO team : teams) {
                teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null));
            }
        }
        this.teams = FXCollections.observableArrayList(teamViewModels);
    }

    private void addTeamsBySportToAvailableList() throws RemoteException {
        SportViewModel selectedSport = (SportViewModel) sportsCombo.getSelectionModel().getSelectedItem();

        ArrayList<TeamDTO> teams;
        teams = this.teamControllerInstance.getBySport(DataProvider.getSession(), selectedSport.getId()).getContents();

        teamViewModels.clear();
        if (teams != null) {
            for (TeamDTO team : teams) {
                teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), team.getMembers(), team.getTrainers(), team.getLeague().getId(), team.getType()));
            }
        }
        this.teams = FXCollections.observableArrayList(teamViewModels);
    }
}
