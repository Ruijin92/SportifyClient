package at.fhv.team2.elements;

import at.fhv.team2.PageProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
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

    private Button dashboard;
    private Button member;
    private Button participation;

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
    }

    /**
     * Adding the Buttons to the Vbox and setting events on them
     */
    public void addingButtons() {

        dashboard = new Button("Dashboard");
        dashboard.setMinWidth(WIDTH);
        dashboard.setMinHeight(HEIGHT);

        dashboard.setOnMouseClicked(event -> {
            pageProvider.switchDashboard();
        });

        member = new Button("Member");
        member.setMinWidth(WIDTH);
        member.setMinHeight(HEIGHT);

        member.setOnMouseClicked(event -> {
            pageProvider.switchMember();
        });

        participation = new Button("Wettkampf");
        participation.setMinWidth(WIDTH);
        participation.setMinHeight(HEIGHT);

        participation.setOnMouseClicked(event -> {
            pageProvider.switchCompetitions();
        });

        vBox.getChildren().addAll(dashboard, member, participation);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
