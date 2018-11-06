package at.fhv.team2.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class Dashboard extends VBox implements Initializable {

    public Dashboard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}

