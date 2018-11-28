package at.fhv.team2.wettkampf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewCompetition extends HBox implements Initializable {


    public HBox hBox;

    public ComboBox sportsCombo;

    public ComboBox leagueCombo;

    public DatePicker datePick;

    public TextField time;

    public ListSelectionView listView;

    public TextField teamName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NewCompetition.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addExternTeam(ActionEvent event) {

    }
}
