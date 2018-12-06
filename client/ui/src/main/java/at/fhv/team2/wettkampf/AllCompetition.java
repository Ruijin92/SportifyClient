package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.SquadMemberDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.member.PersonViewModel;
import at.fhv.team2.roles.Permission;
import at.fhv.team2.wettkampf.ViewModels.CompetitionViewModel;
import at.fhv.team2.wettkampf.ViewModels.ParticipantViewModel;
import at.fhv.team2.wettkampf.ViewModels.SquadViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class AllCompetition extends HBox implements Initializable {

    public HBox hBox;
    public Button changeButton;
    public Button newButton;
    public Button resultButton;
    public Button squadButton;
    public Button squadChangeButton;
    public Button personalCompetition;
    public Button allCompetitions;
    public TextField searchTournament;

    public Button searchButton;

    public ListView listCompetitions;
    private List<CompetitionViewModel> tournaments = new ArrayList<>();
    private ObservableList<CompetitionViewModel> competitionTableList;
    private boolean showAllCompetitions;
    private String role;

    private CompetitionViewModel tournamentDetails;
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
        List<RoleDTO> roles = DataProvider.getSession().getRoles();
        if (roles.get(0).getName().equals("Admin")) {
            role = "Admin";
        }

        newButton.setVisible(Permission.getPermission().createCompetitionPermission());
        resultButton.setVisible(Permission.getPermission().createCompetitionPermission());
        changeButton.setVisible(Permission.getPermission().createCompetitionPermission());
        squadButton.setVisible(Permission.getPermission().createTeamPermission());
        squadChangeButton.setVisible(Permission.getPermission().createTeamPermission());


        changeButton.setDisable(true);
        squadButton.setDisable(true);
        squadChangeButton.setDisable(true);

        this.showAllCompetitions = true;
        this.tournamentControllerInstance = DataProvider.getTournamentControllerInstance();
        showCompetitions();

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
        if (listCompetitions.getSelectionModel().getSelectedItem() != null) {
            CompetitionViewModel selectedCompetition = (CompetitionViewModel) listCompetitions.getSelectionModel().getSelectedItem();
            PageProvider.getPageProvider().switchChangeCompetitions(selectedCompetition.getId());
        }
    }

    public void createCompetition(ActionEvent event) {
        PageProvider.getPageProvider().switchNewComp();
    }

    public void enterResult(ActionEvent event) {
        if (listCompetitions.getSelectionModel().getSelectedItem() != null) {
            CompetitionViewModel selectedCompetition = (CompetitionViewModel) listCompetitions.getSelectionModel().getSelectedItem();
            PageProvider.getPageProvider().switchEnterResult(selectedCompetition.getId());
        }
    }

    public void setSquad(ActionEvent event) {
        if (listCompetitions.getSelectionModel().getSelectedItem() != null) {
            Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();
            PageProvider.getPageProvider().switchTeamSquad((CompetitionViewModel) selectedItem);
        }
    }

    public void changeSquad(ActionEvent event) {
        if (listCompetitions.getSelectionModel().getSelectedItem() != null) {
            Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();
            PageProvider.getPageProvider().switchToChangeTeamSquad((CompetitionViewModel) selectedItem, true);
        }
    }

    private void addCompetitionsToList(List<CompetitionViewModel> competitions) {
        competitionTableList = FXCollections.observableArrayList(competitions);
        listCompetitions.setItems(competitionTableList);
    }

    public void clickItem(MouseEvent event) throws RemoteException {
        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();
        CompetitionViewModel selectedCompetition = (CompetitionViewModel) selectedItem;

        TournamentDTO tournament = null;
        if (selectedCompetition == null) {
            tournament = new TournamentDTO();
        } else {
            tournament = this.tournamentControllerInstance.getEntryDetails(DataProvider.getSession(), selectedCompetition.getId());
        }

        if (selectedItem != null) {
            if (!showAllCompetitions) {
                if (selectedCompetition.getParticipants().get(0).getParticipants() == null || selectedCompetition.getParticipants().get(0).getParticipants().size() < 1) {
                    squadButton.setDisable(false);
                    squadChangeButton.setDisable(true);
                } else {
                    squadButton.setDisable(true);
                    squadChangeButton.setDisable(false);
                }
                if (tournament.getEncounters() == null) {
                    resultButton.setDisable(true);
                } else {
                    resultButton.setDisable(false);
                }
                changeButton.setDisable(false);
                //squadButton.setDisable(true);
                //squadChangeButton.setDisable(false);
            } else {
                if (role.equals("Admin")) {
                    if (tournament.getEncounters() == null) {
                        resultButton.setDisable(true);
                    } else {
                        resultButton.setDisable(false);
                    }
                    changeButton.setDisable(false);
                } else if (selectedCompetition.getEncounters() == null) {
                    resultButton.setDisable(true);
                }
            }
        } else {
            changeButton.setVisible(true);
            squadButton.setDisable(true);
            squadChangeButton.setDisable(true);
        }
    }

    public void searchTournament() {
        String searchQuery = searchTournament.getText();

        Pattern p = Pattern.compile("^" + searchQuery, Pattern.CASE_INSENSITIVE);
        Matcher m;

        if (searchQuery.isEmpty()) {
            addCompetitionsToList(tournaments);
            return;
        }

        List<CompetitionViewModel> filteredCompetitions =
                tournaments.stream()
                        .filter(t -> p.matcher(t.getName()).find())
                        .collect(toList());

        addCompetitionsToList(filteredCompetitions);
    }

    public void showOnlyPersonalCompetitions() {
        this.showAllCompetitions = false;
        showCompetitions();
    }

    public void showAllCompetitions() {
        this.showAllCompetitions = true;
        showCompetitions();
    }

    private void showCompetitions() {
        this.tournaments.clear();
        ArrayList<TournamentDTO> tournamentsForList = new ArrayList<>();
        if (this.showAllCompetitions) {
            if (!role.equals("Admin")) {
                squadButton.setDisable(true);
                squadButton.setVisible(false);
                squadChangeButton.setDisable(true);
                squadChangeButton.setVisible(false);
            }

            ListWrapper<TournamentDTO> allEntries = null;
            try {
                allEntries = tournamentControllerInstance.getAllEntries(DataProvider.getSession());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (allEntries.getContents() == null) {
                showAlert(allEntries.getResponse().getInfoMessage());
            } else {
                tournamentsForList = allEntries.getContents();
                for (TournamentDTO tournament : tournamentsForList) {
                    tournaments.add(new CompetitionViewModel(tournament.getId(), tournament.getName(), null, null, null, null, null));
                }
            }
        } else {
            ListWrapper<TournamentDTO> allTournaments = new ListWrapper<>();
            try {
                allTournaments = tournamentControllerInstance.getTournamentByTrainerId(DataProvider.getSession(), DataProvider.getSession().getMyUserId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (allTournaments.getContents() == null) {
                showAlert(allTournaments.getResponse().getInfoMessage());
            } else
                tournamentsForList = allTournaments.getContents();
            for (TournamentDTO tournament : tournamentsForList) {
                ArrayList<ParticipantViewModel> participants = new ArrayList<>();
                for (ParticipantDTO team : tournament.getTeams()) {
                    ArrayList<SquadViewModel> teamSquad = new ArrayList<>();
                    if (team.getParticipants() != null) {
                        for (SquadMemberDTO participant : team.getParticipants()) {
                            teamSquad.add(new SquadViewModel(new PersonViewModel(participant.getMember().getId(), participant.getMember().getFirstName(), participant.getMember().getLastName(), null, null, null, null, null), participant.isParticipating()));
                        }
                    }
                    participants.add(new ParticipantViewModel(team.getId(), team.getTeam(), team.getTeamName(), teamSquad, null));
                }
                tournaments.add(new CompetitionViewModel(tournament.getId(), tournament.getName(), null, null, null, null, participants));
            }
        }
        addCompetitionsToList(tournaments);
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loading Tournaments error");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
