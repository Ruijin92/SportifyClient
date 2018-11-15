package at.fhv.team2.teams;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class AllTeams extends HBox implements Initializable {

    public HBox hBox;

    public Button changeButton;
    public Button newButton;
    public Button resultButton;
    public Button searchButton;


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
        changeButton.setDisable(true);
        addTeams();
    }

    public void changeTeam(ActionEvent event) {

    }

    public void createTeam(ActionEvent event) {

    }

    private void addTeams() {

    }
}
