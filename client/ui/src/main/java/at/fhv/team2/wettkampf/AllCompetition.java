package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.roles.Permission;
import at.fhv.team2.wettkampf.ViewModels.CompetitionViewModel;
import at.fhv.team2.wettkampf.ViewModels.ParticipantViewModel;
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

    public Button searchButton;

    public ListView listCompetitions;
    private List<CompetitionViewModel> tournaments = new ArrayList<>();
    private ObservableList<CompetitionViewModel> competitionTableList;
    private boolean showAllCompetitions;

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
        CompetitionViewModel selectedCompetition = (CompetitionViewModel) listCompetitions.getSelectionModel().getSelectedItem();
        PageProvider.getPageProvider().switchChangeCompetitions(selectedCompetition.getId());
    }

    public void createCompetition(ActionEvent event) {
        PageProvider.getPageProvider().switchNewComp();
    }

    public void enterResult(ActionEvent event) {
        CompetitionViewModel selectedCompetition = (CompetitionViewModel) listCompetitions.getSelectionModel().getSelectedItem();
        PageProvider.getPageProvider().switchEnterResult(selectedCompetition.getId());
    }

    public void setSquad(ActionEvent event) {
        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();
        PageProvider.getPageProvider().switchTeamSquad((CompetitionViewModel) selectedItem);
    }

    public void changeSquad(ActionEvent event) {
        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();
        PageProvider.getPageProvider().switchToChangeTeamSquad((CompetitionViewModel) selectedItem, true);
    }

    private void addCompetitionsToList(List<CompetitionViewModel> competitions) {
        competitionTableList = FXCollections.observableArrayList(competitions);
        listCompetitions.setItems(competitionTableList);
    }

    public void clickItem(MouseEvent event) {

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            if (!showAllCompetitions) {
                changeButton.setDisable(false);
                squadButton.setDisable(false);
                squadChangeButton.setDisable(false);
            }
        } else {
            changeButton.setVisible(true);
            squadButton.setDisable(true);
            squadChangeButton.setDisable(true);
        }
    }

    public void showOnlyPersonalCompetitions() {
        this.showAllCompetitions = false;
        squadButton.setDisable(false);
        squadButton.setVisible(true);
        squadChangeButton.setDisable(false);
        squadChangeButton.setVisible(true);
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
            squadButton.setDisable(true);
            squadButton.setVisible(false);
            squadChangeButton.setDisable(true);
            squadChangeButton.setVisible(false);

            try {
                tournamentsForList = tournamentControllerInstance.getAllEntries(DataProvider.getSession()).getContents();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            for (TournamentDTO tournament : tournamentsForList) {
                tournaments.add(new CompetitionViewModel(tournament.getId(), tournament.getName(), null, null, null, null, null));
            }
        } else {
            try {
                tournamentsForList = tournamentControllerInstance.getTournamentByTrainerId(DataProvider.getSession(), DataProvider.getSession().getMyUserId()).getContents();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            for (TournamentDTO tournament : tournamentsForList) {
                ArrayList<ParticipantViewModel> participants = new ArrayList<>();
                for (ParticipantDTO team : tournament.getTeams()) {
                    participants.add(new ParticipantViewModel(team.getId(), team.getTeam(), team.getTeamName(), null, null));
                }
                tournaments.add(new CompetitionViewModel(tournament.getId(), tournament.getName(), null, null, null, null, participants));
            }
        }
        addCompetitionsToList(tournaments);
    }
}
