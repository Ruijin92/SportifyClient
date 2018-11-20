package at.fhv.team2.teams;

import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class AllTeams extends HBox implements Initializable {

    //region UI-Buttons
    public Button searchButton;
    public Button newButton;
    public Button changeButton;
    public Button saveButton;
    //endregion
    //region Table
    public TableView table;
    public TableColumn nameTable;
    public TableColumn leagueTable;
    public TableColumn typeTable;

    private List<TeamViewModel> teams;
    private ObservableList<TeamViewModel> teamsTableList;
    //endregion
    //region Detail
    public VBox memberDetailBox;
    public RadioButton internRadio;
    public RadioButton externRadio;
    public TextField nameField;
    public ComboBox sportBox;
    public ComboBox leagueBox;
    public HBox trainerBox;
    public ComboBox trainerCombo;
    public VBox trainerComboVBox;
    public Label sportNameLabel;
    public Label ligaNameLabel;
    private ArrayList<ComboBox> trainerList = null;
    //endregion

    public AllTeams() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AllTeams.fxml"));
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

        ArrayList<TeamDTO> teamEntries = addTestData();
        this.teams = new ArrayList<>();

        memberDetailBox.setDisable(true);

        for (TeamDTO teams : teamEntries) {
            List<PersonDTO> trainers = teams.getTrainers();
            this.teams.add(new TeamViewModel(teams.getName(), null, trainers, teams.getLeague().getName(), teams.getType()));
        }

        saveButton.setDisable(true);
        changeButton.setDisable(true);

        addTeams();
    }

    public void clickItem(MouseEvent event) {

        trainerList = new ArrayList<>();

        memberDetailBox.setDisable(false);
        TeamViewModel teamViewModel = (TeamViewModel) table.getSelectionModel().getSelectedItem();

        changeButton.setDisable(false);
        leagueBox.setDisable(false);

        nameField.setText(teamViewModel.getName());
        String type = teamViewModel.getType();

        if (type.equals("intern")) {
            internRadio.setSelected(true);
            externDetailState(true);
            getTrainerForTeam();
        } else {
            externRadio.setSelected(true);
            externDetailState(false);
        }
    }

    public void createTeam(ActionEvent event) {

        trainerList = new ArrayList<>();

        memberDetailBox.setDisable(false);

        internRadio.setSelected(true);

        saveButton.setDisable(false);
        changeButton.setDisable(true);

        nameField.setText("");
        sportBox.setValue(null);
        leagueBox.setValue(null);
        leagueBox.setDisable(true);

        //only fix to clear ComboBox
        trainerComboVBox.getChildren().removeAll(trainerCombo);
        trainerCombo = new ComboBox();
        trainerCombo.setPrefWidth(107);
        trainerComboVBox.getChildren().add(0,trainerCombo);
    }

    /**
     * get's triggerd if someone presses the CHANGE Button
     *
     * @param event
     */
    public void changeTeam(ActionEvent event) {
        TeamViewModel teamViewModel = (TeamViewModel) table.getSelectionModel().getSelectedItem();
    }

    public void addComboBox(ActionEvent event) {
        int size = trainerComboVBox.getChildren().size();

        ComboBox comboBox = new ComboBox();
        comboBox.setMaxWidth(107);
        comboBox.setPrefWidth(107);

        trainerComboVBox.getChildren().add(size-1,comboBox);
        trainerList.add(comboBox);
        fillComboBoxWithData(comboBox);
    }

    public void deleteComboBox(ActionEvent event) {
        int size = trainerComboVBox.getChildren().size();
        if(size>2){
            trainerComboVBox.getChildren().remove(trainerComboVBox.getChildren().get(size-2));
        }
    }

    private void addTeams() {

        teamsTableList = FXCollections.observableArrayList(teams);

        nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        leagueTable.setCellValueFactory(new PropertyValueFactory<>("league"));
        typeTable.setCellValueFactory(new PropertyValueFactory<>("type"));

        table.setItems(teamsTableList);
    }

    private void getTrainerForTeam() {

        TeamViewModel teamViewModel = (TeamViewModel) table.getSelectionModel().getSelectedItem();
        List<PersonDTO> trainers = teamViewModel.getTrainers();

        ObservableList<PersonDTO> trainerComboList = FXCollections.observableArrayList(trainers);

        trainerCombo.setItems(trainerComboList);
        trainerCombo.setValue(trainerComboList.get(0).getFirstName() + " " + trainerComboList.get(0).getLastName());

        trainerCombo.setConverter(new StringConverter<PersonDTO>() {

            @Override
            public String toString(PersonDTO object) {
                return object.getFirstName() + " " + object.getLastName();
            }

            @Override
            public PersonDTO fromString(String string) {
                return null;
            }
        });
    }

    private void externDetailState(boolean state) {
        if (state) {
            sportNameLabel.setVisible(true);
            ligaNameLabel.setVisible(true);
            sportBox.setVisible(true);
            leagueBox.setVisible(true);
            trainerBox.setVisible(true);
        } else {
            trainerList.clear();
            sportNameLabel.setVisible(false);
            ligaNameLabel.setVisible(false);
            sportBox.setVisible(false);
            leagueBox.setVisible(false);
            trainerBox.setVisible(false);
        }
    }

    private void fillComboBoxWithData(ComboBox comboBox) {

    }

    private ArrayList<TeamDTO> addTestData() {

        ArrayList<TeamDTO> teamEntries = new ArrayList<>();

        LeagueDTO l1 = new LeagueDTO();
        LeagueDTO l2 = new LeagueDTO();

        l1.setName("A-Liga");
        l2.setName("TennisLiga");

        PersonDTO p1 = new PersonDTO();
        PersonDTO p2 = new PersonDTO();
        p1.setFirstName("Uray");
        p1.setLastName("Örnek");
        p2.setFirstName("Peter");
        p2.setLastName("Fister");


        TeamDTO t1 = new TeamDTO();
        TeamDTO t2 = new TeamDTO();
        t1.setName("FC-Dornbirn");
        t1.setLeague(l1);
        t1.setType("intern");
        t1.setMembers(Collections.singletonList(p1));
        t1.setTrainers(Collections.singletonList(p1));
        t2.setName("FC-Feldkirch");
        t2.setLeague(l2);
        t2.setType("intern");
        t1.setMembers(Collections.singletonList(p2));
        t2.setTrainers(Collections.singletonList(p2));

        teamEntries.add(t1);
        teamEntries.add(t2);
        return teamEntries;
    }

}
