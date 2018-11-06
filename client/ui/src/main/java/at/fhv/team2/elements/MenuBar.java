package at.fhv.team2.elements;

import at.fhv.team2.PageProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/5/2018.
 */
public class MenuBar extends VBox implements Initializable {

    @FXML
    public VBox vBox;
    private final int WIDTH = 100;
    private final int HEIGHT = 35;
    private PageProvider pageProvider;

    ArrayList<Button> menuButton = new ArrayList();

    public MenuBar(PageProvider pageProvider) {

        this.pageProvider = pageProvider;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MenuBar.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addingButtons();
    }

    private void addingButtons() {

        Button dashboard = new Button("Dashboard");
        dashboard.setMinWidth(WIDTH);
        dashboard.setMinHeight(HEIGHT);

        dashboard.setOnMouseClicked(event -> {
            pageProvider.switchDashboard();
        });

        Button mitglieder = new Button("Mitglieder");
        mitglieder.setMinWidth(WIDTH);
        mitglieder.setMinHeight(HEIGHT);

        mitglieder.setOnMouseClicked(event -> {
            pageProvider.switchMitglieder();
        });

        Button wettkampf = new Button("Wettkampf");
        wettkampf.setMinWidth(WIDTH);
        wettkampf.setMinHeight(HEIGHT);

       vBox.getChildren().addAll(dashboard, mitglieder, wettkampf);

        menuButton.add(dashboard);
        menuButton.add(mitglieder);
        menuButton.add(wettkampf);
    }

    private void addingListners() {

    }

    public void initialize(URL location, ResourceBundle resources) { }
}
