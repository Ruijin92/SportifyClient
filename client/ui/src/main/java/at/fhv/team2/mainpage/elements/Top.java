package at.fhv.team2.mainpage.elements;

import at.fhv.team2.DataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.roles.Permission;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class Top extends HBox implements Initializable {

    public Label username;
    public Label siteName;
    public Button messageButton;
    private int messageCounter;

    public Top() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Top.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logoutUser(ActionEvent event) throws IOException, NotBoundException {
        DataProvider.logout();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(Permission.getPermission().getUsername());

    }

    public void setSiteName(String name){
        siteName.setText(name);
    }

    public void switchMessage(ActionEvent event) {
        PageProvider.getPageProvider().switchMessages();
    }

    public void setMessageCount(int count){
        messageCounter = messageCounter + count;
        messageButton.setText(messageCounter + "new Messages");
    }
}
