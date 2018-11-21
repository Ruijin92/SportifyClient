package at.fhv.team2.wettkampf;

import at.fhv.team2.roles.Permission;
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
public class AllCompetition extends HBox implements Initializable {

    public HBox hBox;

    public Button changeButton;
    public Button newButton;
    public Button resultButton;
    public Button searchButton;

    private ListView listCompetitions;

    public AllCompetition() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AllCompetitions.fxml"));
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

        newButton.setVisible(Permission.getPermission().createCompetitionPermission());
        resultButton.setVisible(Permission.getPermission().createCompetitionPermission());
        changeButton.setVisible(Permission.getPermission().createCompetitionPermission());

        changeButton.setDisable(true);
        addCompetitions();
    }

    public void changeCompetition(ActionEvent event) {

        Object selectedItem = listCompetitions.getSelectionModel().getSelectedItem();

        if(selectedItem != null){

        }
    }

    public void createCompetition(ActionEvent event) {

    }

    public void enterResult(ActionEvent event) {

    }

    private void addCompetitions() {

    }
}
