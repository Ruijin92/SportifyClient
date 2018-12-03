package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
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

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.temporal.Temporal;
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

    private TournamentDTO loadedTournament;
    private boolean isNew = false;
    private String tournamentId = "5c0574c8ebecef365f0a56b9";
    private String loadedLeagueId;

    public NewCompetition() {
        this.isNew = true;
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

    public NewCompetition(String tournamentId) {
        this.tournamentId = tournamentId;

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
        listView.setCellFactory(view -> {
            ListCell<TeamViewModel> cell = new ListCell<TeamViewModel>() {

                @Override
                public void updateItem(TeamViewModel item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getName());
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });

        this.departmentController = DataProvider.getDepartmentControllerInstance();
        this.teamControllerInstance = DataProvider.getTeamControllerInstance();
        this.tournamentController = DataProvider.getTournamentControllerInstance();

        addSports();

        if (isNew) {
            leagueCombo.setDisable(true);

            sportsCombo.valueProperty().addListener(event -> {
                leagueCombo.setDisable(false);
                addLeague();
                listView.getSourceItems().clear();
                listView.getTargetItems().clear();
                try {
                    addTeamsBySportToAvailableList();
                    listView.getSourceItems().addAll(teams);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            leagueCombo.valueProperty().addListener(event -> {
                listView.getTargetItems().clear();
                listView.getSourceItems().clear();
                try {
                    addTeamsByLeagueToAvailableList();
                    listView.getSourceItems().addAll(teams);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        } else {
            this.loadedTournament = null;
            try {
                this.loadedTournament = this.tournamentController.getEntryDetails(DataProvider.getSession(), this.tournamentId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //Die 2 setter sind nur für Testzwecke da
            this.loadedTournament.setSportsName("Football");
            this.loadedTournament.setLeagueName("New York Premier League");

            int indexSelectedSport = -1;
            int indexSelectedLeague = -1;
            SportViewModel selectedSport = null;
            LeagueViewModel selectedLeague = null;

            for (int i = 0; i < this.allSports.size(); i++) {
                if (this.loadedTournament.getSportsName().equals(this.allSports.get(i).getName())) {
                    selectedSport = this.allSports.get(i);
                    indexSelectedSport = i;
                    if (this.loadedTournament.getLeague() != null && this.loadedTournament.getLeagueName() != null) {
                        for (int j = 0; j < this.allSports.get(i).getLeagues().size(); j++) {
                            if (loadedTournament.getLeagueName().equals(this.allSports.get(i).getLeagues().get(j).getName())) {
                                selectedLeague = this.allSports.get(i).getLeagues().get(j);
                                this.loadedLeagueId = selectedLeague.getId();
                                indexSelectedLeague = j;
                            }
                        }
                    }
                }
            }
            this.sportsCombo.getSelectionModel().select(indexSelectedSport);
            addLeague();
            if (indexSelectedLeague != -1) {
                this.leagueCombo.getSelectionModel().select(indexSelectedLeague);
            }
            this.sportsCombo.setDisable(true);
            this.leagueCombo.setDisable(true);

            this.tournamentName.setText(this.loadedTournament.getName());
            /*this.loadedTournament.getDate();
            this.datePick.setValue(LocalDate.of());*/
            //Filtern welche Teams bereits dabei sind (rechte seite) und welche auf der linke noch angezeigt werden müssen
            try {
                addTeamsByLeagueToAvailableList();
                listView.getSourceItems().addAll(this.teams);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            ArrayList<TeamViewModel> loadedTeams = new ArrayList<>();
            for (ParticipantDTO team : loadedTournament.getTeams()) {
                loadedTeams.add(new TeamViewModel(team.getId(), team.getTeamName(), null, null, null, null));
            }
        }
    }

    public void addExternTeam(ActionEvent event) {
        listView.getSourceItems().add(new TeamViewModel(null, teamName.getText(), null, null, null, null));
    }

    public void saveComp(ActionEvent event) throws RemoteException {
        ObservableList targetItems = listView.getTargetItems();
        List<TeamViewModel> list = (List<TeamViewModel>) targetItems.stream().collect(Collectors.toList());

        ArrayList<ParticipantDTO> participantTeams = new ArrayList<>();

        for (TeamViewModel team: list) {
            participantTeams.add(new ParticipantDTO(null, team.getId(), team.getName(), null, null, ModificationType.MODIFIED));
        }

        LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();
        String leagueId = null;
        if (selectedLeague != null) {
            leagueId = selectedLeague.getId();
        }
        TournamentDTO tournament = new TournamentDTO(null, tournamentName.getText(), leagueId, null, null, datePick.getValue(),null, participantTeams, null, ModificationType.MODIFIED);

        //savedTournament --> Um zu überprüfen ob alles erfolgreich in die Datenbank gespeichert wurde.
        TournamentDTO savedTournament = this.tournamentController.saveOrUpdateEntry(DataProvider.getSession(), tournament);

        //Encoutner
        PageProvider.getPageProvider().switchEncounter(savedTournament);
    }

    private void addSports() {

        ArrayList<SportDTO> sports = null;

        try {
            ResponseMessageDTO response = this.departmentController.getAllSportEntriesFull(DataProvider.getSession()).getResponse();
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
        if (isNew) {
            LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();

            ArrayList<TeamDTO> teams = this.teamControllerInstance.getByLeague(DataProvider.getSession(), selectedLeague.getId()).getContents();

            teamViewModels.clear();
            if (teams != null) {
                for (TeamDTO team : teams) {
                    teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null));
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
        } else {
            ArrayList<TeamDTO> teams = this.teamControllerInstance.getByLeague(DataProvider.getSession(), this.loadedLeagueId).getContents();

            this.teamViewModels.clear();
            for (int i = 0; i < this.loadedTournament.getTeams().size(); i++) {
                for (TeamDTO team : teams) {
                    if (!team.getName().equals(this.loadedTournament.getTeams().get(i).getTeamName())) {
                        this.teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null));
                    }
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
        }
    }

    private void addTeamsBySportToAvailableList() throws RemoteException {
            SportViewModel selectedSport = (SportViewModel) sportsCombo.getSelectionModel().getSelectedItem();

            ArrayList<TeamDTO> teams;
            teams = this.teamControllerInstance.getBySport(DataProvider.getSession(), selectedSport.getId()).getContents();

            teamViewModels.clear();
            if (teams != null) {
                for (TeamDTO team : teams) {
                    teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null));
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
    }

    private void addLoadedTeamsToSelectionList(ArrayList<TeamViewModel> loadedTeams) {
        this.teams = FXCollections.observableArrayList(teamViewModels);
        listView.getTargetItems().setAll(loadedTeams);
    }
}
