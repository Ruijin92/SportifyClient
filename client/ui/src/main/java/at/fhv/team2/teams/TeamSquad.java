package at.fhv.team2.teams;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeamSquad extends VBox implements Initializable {

    private TeamViewModel team;

    public TeamSquad(TeamViewModel team) {
        this.team = team;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TeamSquad.fxml"));
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

    public void teamSquadSave(ActionEvent event) {

    }
}
