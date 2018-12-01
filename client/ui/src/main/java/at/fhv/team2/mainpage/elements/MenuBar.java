package at.fhv.team2.mainpage.elements;

import at.fhv.team2.PageProvider;
import at.fhv.team2.roles.Permission;
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

    private final int WIDTH = 120;
    private final int HEIGHT = 45;
    private PageProvider pageProvider;

    private Button dashboard;
    private Button member;
    private Button competition;
    private Button teams;

    public MenuBar() {

        this.pageProvider = PageProvider.getPageProvider();

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

        competition = new Button("Wettkampf");
        competition.setMinWidth(WIDTH);
        competition.setMinHeight(HEIGHT);


        competition.setOnMouseClicked(event -> {
            pageProvider.switchCompetitions();
        });

        teams = new Button("Mannschaft");
        teams.setMinWidth(WIDTH);
        teams.setMinHeight(HEIGHT);

        teams.setOnMouseClicked(event -> {
            pageProvider.switchTeams();
        });

        vBox.getChildren().add(dashboard);

        if(Permission.getPermission().viewMemberPermission()){
            vBox.getChildren().add(member);
        }
        if(Permission.getPermission().viewCompetitionPermission()){
            vBox.getChildren().add(competition);
        }
        if(Permission.getPermission().viewTeamPermission()){
            vBox.getChildren().add(teams);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
