package at.fhv.team2.mainpage;

import at.fhv.team2.PageProvider;
import at.fhv.team2.mainpage.elements.Bottom;
import at.fhv.team2.mainpage.elements.MenuBar;
import at.fhv.team2.mainpage.elements.Top;
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
    public BorderPane mainPane;

    private PageProvider pageProvider = PageProvider.getPageProvider();


    public void initialize(URL location, ResourceBundle resources) {
        setMainPageUp();
    }

    private void setMainPageUp() {
        pageProvider.setMainPage(this);

        Top topBar = new Top();
        MenuBar menubar = new MenuBar();
        menubar.addingButtons();
        menubar.setTopBar(topBar);

        //Dashboard as standard
        pageProvider.switchDashboard();

        mainPane.setLeft(menubar);
        mainPane.setTop(topBar);
        mainPane.setBottom(new Bottom());

    }
}
