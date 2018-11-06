package at.fhv.team2;

import at.fhv.team2.elements.MenuBar;
import at.fhv.team2.elements.Top;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/5/2018.
 */
public class MainPage implements Initializable {

    @FXML
    public BorderPane anchorPane;
    private PageProvider pageProvider = new PageProvider(this);

    public void initialize(URL location, ResourceBundle resources) {

        setMainPageUp();

    }

    private void setMainPageUp() {
        MenuBar menubar = new MenuBar(pageProvider);
        anchorPane.setLeft(menubar);
        anchorPane.setTop(new Top());
        anchorPane.setBottom(new Top());
    }

}
