package at.fhv.team2.mitglieder;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class Mitglieder extends HBox implements Initializable {

    public Mitglieder() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Mitglieder.fxml"));
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
}
