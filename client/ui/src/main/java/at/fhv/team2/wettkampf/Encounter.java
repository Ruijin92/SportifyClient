package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.model.tournament.TournamentDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Encounter extends HBox implements Initializable {

    private TournamentDTO tournamentDTO;

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

    }

    void addEncounter(ActionEvent event) {

    }

    void addResult(ActionEvent event) {

    }

    void clickItem(MouseEvent event) {

    }

    void saveEncounter(ActionEvent event) {

    }
}
