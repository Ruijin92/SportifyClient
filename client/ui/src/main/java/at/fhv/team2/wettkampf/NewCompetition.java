package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ListWrapper;
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
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import org.controlsfx.control.ListSelectionView;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private String sportId;
    private String leagueId;

    private TournamentDTO loadedTournament;
    private boolean changed;
    private String tournamentId;
    private String loadedLeagueId;
    private String loadedTournamentName;
    private String loadedTournamentDate;

    public NewCompetition() {
        this.changed = true;
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
                        setText(item.getType() + "   |   " + item.getName());
                        backgroundProperty();
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

        if (changed) {
            this.loadedTournamentDate = "";
            this.loadedTournamentName = "";
            leagueCombo.setDisable(true);

            sportsCombo.valueProperty().addListener(event -> {
                leagueCombo.setDisable(false);
                listView.getSourceItems().clear();
                listView.getTargetItems().clear();
                try {
                    addTeamsBySportToAvailableList();
                    listView.getSourceItems().addAll(teams);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                addLeague();
            });

            leagueCombo.valueProperty().addListener(event -> {
                listView.getTargetItems().clear();
                listView.getSourceItems().clear();
                addTeamsByLeagueToAvailableList();
                listView.getSourceItems().addAll(teams);
            });
        } else {
            this.loadedTournament = null;
            try {
                this.loadedTournament = this.tournamentController.getEntryDetails(DataProvider.getSession(), this.tournamentId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            this.loadedTournamentDate = this.loadedTournament.getDate().toString();
            this.loadedTournamentName = this.loadedTournament.getName();

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
            this.datePick.setValue(this.loadedTournament.getDate());

            //Filtern welche Teams bereits dabei sind (rechte seite) und welche auf der linke noch angezeigt werden müssen
            if (loadedTournament.getLeague() != null) {
                addTeamsByLeagueToAvailableList();
            } else {
                try {
                    addTeamsBySportToAvailableList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            listView.getSourceItems().addAll(this.teams);

            ArrayList<TeamViewModel> loadedTeams = new ArrayList<>();
            for (ParticipantDTO team : loadedTournament.getTeams()) {
                TeamViewModel teamModel = new TeamViewModel(team.getTeam(), team.getTeamName(), null, null, null, null, ModificationType.NONE);
                teamModel.setLoadedParticipantId(team.getId());
                loadedTeams.add(teamModel);
            }
            listView.getTargetItems().addAll(loadedTeams);
        }
    }

    public void addExternTeam(ActionEvent event) {
        listView.getSourceItems().add(new TeamViewModel(null, teamName.getText(), null, null, null, null, ModificationType.MODIFIED));
        teamName.clear();
        this.changed = true;
    }

    public void saveComp(ActionEvent event) throws RemoteException {
        if (sportsCombo.getSelectionModel().getSelectedItem() == null) {
            showAlert("Sie müssen mindenstens die Sportart wählen - Die Liga ist Optional!");
            return;
        } else if (listView.getTargetItems().size() == 0) {
            showAlert("Sie müssen mindenstens 1 Team zum Tunier hinzufügen!");
            return;
        } else if (datePick.getValue() == null) {
            showAlert("Sie müssen ein Datum auswählen!");
            return;
        } else if (tournamentName.getText().equals("")) {
            showAlert("Sie müssen einen Namen für das Tunier eingeben!");
            return;

        } else {
            checkIfDateOrNameHasChanged();
            if (changed) {
                ObservableList targetItems = listView.getTargetItems();
                List<TeamViewModel> list = (List<TeamViewModel>) targetItems.stream().collect(Collectors.toList());
                if (loadedTournament != null) {
                    checkIfTeamsWereRemovedFromTournament();
                    ArrayList<ParticipantDTO> participantTeams = new ArrayList<>();

                    for (TeamViewModel teamViewModel : list) {
                        TeamDTO newExternTeam = null;
                        if (teamViewModel.getId() == null) {
                            LeagueDTO league = getLeagueDTO();
                            newExternTeam = new TeamDTO(null, teamViewModel.getName(), null, null, league, "Extern", null);
                            ResponseMessageDTO responseOfNewTeamSaved = this.teamControllerInstance.saveOrUpdateEntry(DataProvider.getSession(), newExternTeam);
                            if (responseOfNewTeamSaved.getContextId() != null) {
                                TeamDTO savedTeam = this.teamControllerInstance.getById(DataProvider.getSession(), responseOfNewTeamSaved.getContextId());
                                participantTeams.add(new ParticipantDTO(null, savedTeam.getId(), savedTeam.getName(), null, null, ModificationType.MODIFIED));
                            }
                        } else {


                            ModificationType modifiedStatus = null;
                            if (teamViewModel.getModificationType() == ModificationType.NONE) {
                                modifiedStatus = ModificationType.NONE;
                            } else if (teamViewModel.getModificationType() == ModificationType.MODIFIED) {
                                modifiedStatus = ModificationType.MODIFIED;
                            } else if (teamViewModel.getModificationType() == ModificationType.REMOVED) {
                                modifiedStatus = ModificationType.REMOVED;
                            }

                            String participantId = null;
                            if (teamViewModel.getLoadedParticipantId() != null) {
                                participantId = teamViewModel.getLoadedParticipantId();
                            }
                            participantTeams.add(new ParticipantDTO(participantId, teamViewModel.getId(), teamViewModel.getName(), null, null, ModificationType.MODIFIED));
                        }
                    }

                    for (ParticipantDTO team : loadedTournament.getTeams()) {
                        boolean deleted = true;
                        List<TeamViewModel> targetTeams = (List<TeamViewModel>) targetItems.stream().collect(Collectors.toList());
                        for (TeamViewModel targetTeam : targetTeams) {
                            if (team.getTeamName().equals(targetTeam.getName())) {
                                deleted = false;
                            }
                        }
                        if (deleted) {
                            ParticipantDTO deletedParticipant = team;
                            deletedParticipant.setModificationType(ModificationType.REMOVED);
                            participantTeams.add(deletedParticipant);
                        }
                    }

                    String leagueId = null;
                    if (loadedLeagueId != null) {
                        leagueId = loadedLeagueId;
                    }
                    String sportId = null;
                    if (this.sportId != null) {
                        sportId = this.sportId;
                    }
                    TournamentDTO tournamentDTO = new TournamentDTO(loadedTournament.getId(), tournamentName.getText(), leagueId, sportId, loadedTournament.getLeagueName(), loadedTournament.getSportsName(),
                            datePick.getValue(), loadedTournament.getEncounters(), participantTeams, null, ModificationType.MODIFIED);

                    //savedTournament --> Um zu überprüfen ob alles erfolgreich in die Datenbank gespeichert wurde.
                    //TODO: Name des Tuniers und Datum wird nicht überschrieben.
                    TournamentDTO savedTournament = this.tournamentController.saveOrUpdateEntry(DataProvider.getSession(), tournamentDTO);

                    PageProvider.getPageProvider().switchCompetitions();
                } else {
                    ArrayList<ParticipantDTO> participantTeams = new ArrayList<>();

                    for (TeamViewModel team : list) {
                        TeamDTO newExternTeam = null;
                        if (team.getId() == null) {
                            LeagueDTO league = getLeagueDTO();
                            newExternTeam = new TeamDTO(null, team.getName(), null, null, league, "Extern", null);
                            ResponseMessageDTO responseOfNewTeamSaved = this.teamControllerInstance.saveOrUpdateEntry(DataProvider.getSession(), newExternTeam);
                            if (responseOfNewTeamSaved.getContextId() != null) {
                                TeamDTO savedTeam = this.teamControllerInstance.getById(DataProvider.getSession(), responseOfNewTeamSaved.getContextId());
                                participantTeams.add(new ParticipantDTO(null, savedTeam.getId(), savedTeam.getName(), null, null, ModificationType.MODIFIED));
                            }
                        } else {
                            participantTeams.add(new ParticipantDTO(null, team.getId(), team.getName(), null, null, ModificationType.MODIFIED));

                        }
                    }

                    LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();
                    String leagueId = null;
                    if (selectedLeague != null) {
                        leagueId = selectedLeague.getId();
                    }

                    TournamentDTO tournament = new TournamentDTO(null, tournamentName.getText(), leagueId, this.sportId, null, null, datePick.getValue(), null, participantTeams, null, ModificationType.MODIFIED);

                    //savedTournament --> Um zu überprüfen ob alles erfolgreich in die Datenbank gespeichert wurde.
                    TournamentDTO savedTournament = this.tournamentController.saveOrUpdateEntry(DataProvider.getSession(), tournament);

                    //Encoutner
                    PageProvider.getPageProvider().switchEncounter(savedTournament);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Du hast nix geändert.");
            }
        }

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

    private void addTeamsByLeagueToAvailableList() {
        if (changed) {
            if (leagueCombo.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();
            this.leagueId = selectedLeague.getId();

            ListWrapper<TeamDTO> teamEntries = new ListWrapper<>();
            try {
                teamEntries = this.teamControllerInstance.getByLeague(DataProvider.getSession(), selectedLeague.getId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ArrayList<TeamDTO> teams = new ArrayList<>();

            if (teamEntries.getContents() != null) {
                teams = teamEntries.getContents();
            }

            teamViewModels.clear();
            if (teams != null) {
                for (TeamDTO team : teams) {
                    teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null, ModificationType.MODIFIED));
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
        } else {
            ListWrapper<TeamDTO> wrapper = null;
            try {
                wrapper = this.teamControllerInstance.getByLeague(DataProvider.getSession(), loadedTournament.getLeague());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ArrayList<TeamDTO> teams = new ArrayList<>();
            if (wrapper.getContents() != null) {
                teams = wrapper.getContents();
            }
            this.teamViewModels.clear();

            for (TeamDTO team : teams) {
                boolean matched = false;
                for (int i = 0; i < this.loadedTournament.getTeams().size(); i++) {
                    if (team.getName().equals(this.loadedTournament.getTeams().get(i).getTeamName())) {
                        matched = true;
                    }
                }
                if (matched == false) {
                    this.teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null, ModificationType.MODIFIED));
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
        }
        this.changed = true;
    }

    private void addTeamsBySportToAvailableList() throws RemoteException {
        if (changed) {
            SportViewModel selectedSport = (SportViewModel) sportsCombo.getSelectionModel().getSelectedItem();
            this.sportId = selectedSport.getId();

            ArrayList<TeamDTO> teams;
            teams = this.teamControllerInstance.getBySport(DataProvider.getSession(), selectedSport.getId()).getContents();

            teamViewModels.clear();
            if (teams != null) {
                for (TeamDTO team : teams) {
                    teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null, ModificationType.MODIFIED));
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
        } else {
            //SportDTO sportByLeagueId = this.departmentController.getSportByLeagueId(DataProvider.getSession(), this.loadedLeagueId);
            this.sportId = loadedTournament.getSport();
            ListWrapper<TeamDTO> wrapper = null;
            try {
                wrapper = this.teamControllerInstance.getBySport(DataProvider.getSession(), loadedTournament.getSport());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ArrayList<TeamDTO> teams = new ArrayList<>();
            if (wrapper.getContents() != null) {
                teams = wrapper.getContents();
            }
            this.teamViewModels.clear();

            for (TeamDTO team : teams) {
                boolean matched = false;
                for (int i = 0; i < this.loadedTournament.getTeams().size(); i++) {
                    if (team.getName().equals(this.loadedTournament.getTeams().get(i).getTeamName())) {
                        matched = true;
                    }
                }
                if (matched == false) {
                    this.teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), null, null, null, null, ModificationType.MODIFIED));
                }
            }
            this.teams = FXCollections.observableArrayList(teamViewModels);
        }
        this.changed = true;

    }

    private void addLoadedTeamsToSelectionList(ArrayList<TeamViewModel> loadedTeams) {
        this.teams = FXCollections.observableArrayList(teamViewModels);
        listView.getTargetItems().setAll(loadedTeams);
    }

    private void checkIfDateOrNameHasChanged() {
        if (this.changed != true && (!this.loadedTournamentName.equals(tournamentName.getText()) || !this.loadedTournamentDate.equals(datePick.getValue().toString()))) {
            this.changed = true;
        }
    }

    private void checkIfTeamsWereRemovedFromTournament() {
        if (loadedTournament.getTeams().size() > listView.getTargetItems().size()) {
            this.changed = true;
        }
    }

    private LeagueDTO getLeagueDTO() throws RemoteException {
        String leagueId = null;
        LeagueDTO leagueForExternTeam = null;
        if (leagueCombo.getSelectionModel().getSelectedItem() != null) {
            LeagueViewModel selectedLeague = (LeagueViewModel) leagueCombo.getSelectionModel().getSelectedItem();
            leagueId = selectedLeague.getId();
            leagueForExternTeam = this.departmentController.getLeagueById(DataProvider.getSession(), leagueId);
        } else {
            SportViewModel selectedLeague = (SportViewModel) sportsCombo.getSelectionModel().getSelectedItem();
            String sportId = selectedLeague.getId();
            ListWrapper<LeagueDTO> leaguesBySportId = this.departmentController.getLeaguesBySportId(DataProvider.getSession(), sportId);

            if (leaguesBySportId != null) {
                Random rand = new Random();
                int value = rand.nextInt(leaguesBySportId.getContents().size() - 1);
                leagueForExternTeam = leaguesBySportId.getContents().get(value);
            }
        }
        return leagueForExternTeam;
    }

    private void showAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Can't save the Tournament!");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
