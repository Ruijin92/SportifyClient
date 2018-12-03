package at.fhv.team2.wettkampf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewCompetition extends HBox implements Initializable {

    public HBox hBox;
    public ComboBox sportsCombo;
    public ComboBox leagueCombo;
    public DatePicker datePick;
    public TextField time;
    public ListSelectionView listView;
    public TextField teamName;

    private  ObservableList<ParticipantViewModel> participants;

    public NewCompetition(){
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

        addSports();

        sportsCombo.valueProperty().addListener(event ->  {
            leagueCombo.setDisable(false);
            addLeague();
            listView.getSourceItems().clear();
            listView.getTargetItems().clear();
        });

        leagueCombo.valueProperty().addListener(event -> {
            listView.getTargetItems().clear();
            listView.getSourceItems().clear();
            addParticipantsToList();
        });

    }

    public void addExternTeam(ActionEvent event) {
        listView.getSourceItems().add(new ParticipantViewModel(null,teamName.getText(),null,null));
    }
    public void saveComp(ActionEvent event){

    }

    private void addParticipantsToList(){
        participants = FXCollections.observableArrayList(addTestData());
        listView.getSourceItems().add(participants);
    }

    private void addSports(){
        ObservableList<String> allItems = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five");
        sportsCombo.setItems(allItems);
    }

    private void addLeague(){
        ObservableList<String> allItems = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five");
        leagueCombo.setItems(allItems);
    }

    //FIXME: only for testing
    private List<ParticipantViewModel> addTestData(){

        List<ParticipantViewModel> list = new ArrayList<>();
        list.add(new ParticipantViewModel("22","FC-Dornbirn","FC",null));
        return list;
    }
}
