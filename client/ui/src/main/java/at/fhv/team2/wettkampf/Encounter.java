package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.wettkampf.ViewModels.EncounterViewModel;
import at.fhv.team2.wettkampf.ViewModels.ParticipantViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class Encounter extends HBox implements Initializable {

    private TournamentDTO tournamentDTO;
    private ArrayList<EncounterViewModel> encounters;
    private ObservableList<EncounterViewModel> tableEncounters;

    public TableView table;
    public TableColumn homeColumn;
    public TableColumn guestColumn;
    public TableColumn homeResultColumn;
    public TableColumn guestResultColumn;
    public TableColumn dateColumn;
    public ComboBox homeCombo;
    public ComboBox guestCombo;
    public Button encounterAddButton;
    public TextField homeResult;
    public TextField guestResult;
    public Button resultButton;
    public Button saveButton;


    public Encounter(TournamentDTO tournamentDTO) {
        this.tournamentDTO = tournamentDTO;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Encounters.fxml"));
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
        addTeamsToComboBox();
        addToTable();
        tableEncounters = FXCollections.observableArrayList();
        table.setItems(tableEncounters);
    }

    public void addEncounter(ActionEvent event) {
        Object homeTeam = homeCombo.getSelectionModel().getSelectedItem();
        Object guestTeam = guestCombo.getSelectionModel().getSelectedItem();

        //TODO:Date wird aus TournamentDTO gezogen + Time wird in diesem Screen gesetzt
        encounters = new ArrayList<>();
        encounters.add(new EncounterViewModel(null, null, null, (ParticipantViewModel) homeTeam, (ParticipantViewModel) guestTeam, 0, 0));
        tableEncounters.addAll(FXCollections.observableArrayList(encounters));
    }

    public void addResult(ActionEvent event) {
        EncounterViewModel encounter = (EncounterViewModel) table.getSelectionModel().getSelectedItem();
        encounter.setHomePoints(Integer.parseInt(homeResult.getText()));
        encounter.setGuestPoints(Integer.parseInt(guestResult.getText()));
        homeResult.clear();
        guestResult.clear();
    }

    public void clickItem(MouseEvent event) {

    }

    public void saveEncounter(ActionEvent event) {
        ArrayList<EncounterDTO> encounterDTOS = new ArrayList<>();
        for (EncounterViewModel encounter : encounters) {
            encounterDTOS.add(new EncounterDTO(null, null, 0, encounter.getHomeTeamModel().getId(), encounter.getGuestTeamModel().getId(), encounter.getHomePoints(), encounter.getGuestPoints(), null, ModificationType.MODIFIED));
        }
    }

    private void addTeamsToComboBox() {
        ArrayList<ParticipantViewModel> teams = new ArrayList<>();
        for (ParticipantDTO team : tournamentDTO.getTeams()) {
            teams.add(new ParticipantViewModel(team.getId(), team.getTeam(), team.getTeamName(), null));
        }
        ObservableList<ParticipantViewModel> allItems = FXCollections.observableArrayList(teams);
        homeCombo.setItems(allItems);
        homeCombo.setConverter(new StringConverter<ParticipantViewModel>() {
            @Override
            public String toString(ParticipantViewModel object) {
                return object.getTeamName();
            }

            @Override
            public ParticipantViewModel fromString(String string) {
                return null;
            }
        });
        guestCombo.setItems(allItems);
        guestCombo.setConverter(new StringConverter<ParticipantViewModel>() {
            @Override
            public String toString(ParticipantViewModel object) {
                return object.getTeamName();
            }

            @Override
            public ParticipantViewModel fromString(String string) {
                return null;
            }
        });
    }

    private void addToTable() {
        homeColumn.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
        guestColumn.setCellValueFactory(new PropertyValueFactory<>("guestTeam"));
        homeResultColumn.setCellValueFactory(new PropertyValueFactory<>("homePoints"));
        guestResultColumn.setCellValueFactory(new PropertyValueFactory<>("guestPoints"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    }
}
