package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.interfacesReturn.ITeamControllerReturn;
import at.fhv.sportsclub.interfacesReturn.ITournamentControllerReturn;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.DataProviderFactory;
import at.fhv.team2.IDataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.wettkampf.ViewModels.EncounterViewModel;
import at.fhv.team2.wettkampf.ViewModels.ParticipantViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;

public class Encounter extends HBox implements Initializable {

    private TournamentDTO tournamentDTO;
    private String tournamentId;
    private ArrayList<EncounterViewModel> encounters;
    private ObservableList<EncounterViewModel> tableEncounters;

    private boolean changed;
    private ArrayList<EncounterDTO> removedEncounters;

    private ITournamentControllerReturn tournamentController;
    private ITeamControllerReturn teamController;
    private IDataProvider dataProvider;

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
    public Button removeEncounter;


    public Encounter(TournamentDTO tournamentDTO) {
        this.tournamentDTO = tournamentDTO;
        changed = true;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Encounters.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Encounter(String tournamentId) {
        this.tournamentId = tournamentId;

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
        dataProvider = DataProviderFactory.getCurrentDataProvider();

        this.tournamentController = dataProvider.getTournamentControllerInstance();
        this.teamController = dataProvider.getTeamControllerInstance();

        removeEncounter.setDisable(true);
        tableEncounters = FXCollections.observableArrayList();
        encounters = new ArrayList<>();
        removedEncounters = new ArrayList<>();

        if (this.tournamentDTO == null) {
            try {
                this.tournamentDTO = this.tournamentController.getEntryDetails(dataProvider.getSession(), this.tournamentId);
                addLoadedEncountersToList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        try {
            addTeamsToComboBox();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        addToTable();
        table.setItems(tableEncounters);
    }

    public void addEncounter(ActionEvent event) {
        Object homeTeam = homeCombo.getSelectionModel().getSelectedItem();
        Object guestTeam = guestCombo.getSelectionModel().getSelectedItem();

        //TODO:Date wird aus TournamentDTO gezogen + Time wird in diesem Screen gesetzt
        encounters.add(new EncounterViewModel(null, tournamentDTO.getDate().toString(), null, (ParticipantViewModel) homeTeam, (ParticipantViewModel) guestTeam, 0, 0, ModificationType.MODIFIED));
        tableEncounters.clear();
        tableEncounters.addAll(FXCollections.observableArrayList(encounters));

        changed = true;
    }

    public void addResult(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            EncounterViewModel encounter = (EncounterViewModel) table.getSelectionModel().getSelectedItem();
            encounter.setHomePoints(Integer.parseInt(homeResult.getText()));
            encounter.setGuestPoints(Integer.parseInt(guestResult.getText()));
            encounter.setModificationType(ModificationType.MODIFIED);
            homeResult.clear();
            guestResult.clear();

            changed = true;
        }
    }

    public void clickItem(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            removeEncounter.setDisable(false);
        }
    }

    public void saveEncounter(ActionEvent event) throws RemoteException {
        if (changed) {
            ArrayList<EncounterDTO> encounterDTOS = new ArrayList<>();
            for (EncounterViewModel encounter : encounters) {
                String date = encounter.getDate();
                String[] dateFormat = date.split("-");
                String id = null;
                if (encounter.getId() != null) {
                    id= encounter.getId();
                }
                encounterDTOS.add(new EncounterDTO(id, LocalDate.of(Integer.parseInt(dateFormat[0]),
                        Integer.parseInt(dateFormat[1]), Integer.parseInt(dateFormat[2])),
                        0, encounter.getHomeTeamModel().getId(), encounter.getGuestTeamModel().getId(),
                        encounter.getHomePoints(), encounter.getGuestPoints(), null, encounter.getModificationType()));
            }
            for (EncounterDTO removedEncounter : removedEncounters) {
                encounterDTOS.add(removedEncounter);
            }
            tournamentDTO.setEncounters(encounterDTOS);
            TournamentDTO updatedTournament = this.tournamentController.saveOrUpdateEntry(dataProvider.getSession(), tournamentDTO);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Du hast nix geändert.");
        }
        PageProvider.getPageProvider().switchCompetitions();
    }

    public void removeEncounter() {
        EncounterViewModel encounter = (EncounterViewModel) table.getSelectionModel().getSelectedItem();
        int removedIndex = table.getSelectionModel().getFocusedIndex();

        this.encounters.remove(removedIndex);
        String[] date = encounter.getDate().split("-");
        removedEncounters.add(new EncounterDTO(encounter.getId(), LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[1])), 0, encounter.getHomeTeam(), encounter.getGuestTeam(),
                                encounter.getHomePoints(), encounter.getGuestPoints(), null, ModificationType.REMOVED));

        tableEncounters.clear();
        tableEncounters.addAll(FXCollections.observableArrayList(encounters));
        changed = true;
    }

    private void addTeamsToComboBox() throws RemoteException {
        ArrayList<ParticipantViewModel> teams = new ArrayList<>();
        for (ParticipantDTO team : tournamentDTO.getTeams()) {
            String teamName = null;
            if (team.getTeamName() == null) {
                TeamDTO teamDTO = this.teamController.getById(dataProvider.getSession(), team.getTeam());
                teamName = teamDTO.getName();
            } else {
                teamName = team.getTeamName();
            }
            teams.add(new ParticipantViewModel(team.getId(), team.getTeam(), team.getTeamName(), null, ModificationType.NONE));
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

    private void addLoadedEncountersToList() throws RemoteException {
        HashMap<String, ParticipantDTO> participants = new HashMap<>();
        for (ParticipantDTO team : this.tournamentDTO.getTeams()) {
            participants.put(team.getId(), team);
        }


        for (EncounterDTO encounter : this.tournamentDTO.getEncounters()) {
            ParticipantDTO homeTeam = participants.get(encounter.getHomeTeam());
            ParticipantViewModel homeTeamModel = new ParticipantViewModel(homeTeam.getId(), homeTeam.getTeam(), homeTeam.getTeamName(), null, ModificationType.NONE);
            ParticipantDTO guestTeam = participants.get(encounter.getGuestTeam());
            ParticipantViewModel guestTeamModel = new ParticipantViewModel(guestTeam.getId(), guestTeam.getTeam(), guestTeam.getTeamName(), null, ModificationType.NONE);
            this.encounters.add(new EncounterViewModel(encounter.getId(), encounter.getDate().toString(), null, homeTeamModel, guestTeamModel, encounter.getHomePoints(), encounter.getGuestPoints(), ModificationType.NONE));
        }

        tableEncounters.addAll(FXCollections.observableArrayList(this.encounters));
    }
}
