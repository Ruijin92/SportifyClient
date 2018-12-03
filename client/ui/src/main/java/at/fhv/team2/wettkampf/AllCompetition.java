package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.PageProvider;
import at.fhv.team2.roles.Permission;
import at.fhv.team2.wettkampf.ViewModels.CompetitionViewModel;
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

    public Button searchButton;

    public ListView listCompetitions;
    private List<CompetitionViewModel> tournaments = new ArrayList<>();
    private ObservableList<CompetitionViewModel> competitionTableList;

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


        changeButton.setDisable(true);
        squadButton.setDisable(true);

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
        String tournamentId = "adksda";
        PageProvider.getPageProvider().switchChangeCompetitions(tournamentId);
    }

    public void createCompetition(ActionEvent event) {
        PageProvider.getPageProvider().switchNewComp();
    }

    public void enterResult(ActionEvent event) {

    }

    public void setSquad(ActionEvent event) {

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();
        PageProvider.getPageProvider().switchTeamSquad((CompetitionViewModel) selectedItem);
    }

    private void addCompetitionsToList(List<CompetitionViewModel> competitions) {
        competitionTableList = FXCollections.observableArrayList(competitions);
        listCompetitions.setItems(competitionTableList);
    }

    public void clickItem(MouseEvent event) {

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            changeButton.setDisable(false);
            squadButton.setDisable(false);
        } else {
            changeButton.setVisible(true);
            squadButton.setDisable(true);
        }
    }

    //FIXME: wird nur zu test zwecken benutzt
    private ArrayList<TournamentDTO> addTestData() {
        ArrayList<TournamentDTO> list = new ArrayList<>();
        list.add(new TournamentDTO("3", "Wettkampf", null, null, null,null, null, null, null, null));
        return list;
    }
}
