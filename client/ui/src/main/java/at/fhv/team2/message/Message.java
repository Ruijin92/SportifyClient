package at.fhv.team2.message;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alex on 26.11.2018.
 */

public class Message implements Initializable {

    public ListView messageList;
    public TextArea messageView;
    public Button confirmMatch, cancelMatch;

    public Message() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Message.fxml"));
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
        // Lade alle Nachrichten
    }

    public void openMessage() {

    }

    public void confirmMessage() {

    }

    public void cancelMessage() {

    }
}
