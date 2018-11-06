package at.fhv.team2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Uray Örnek on 11/5/2018.
 */
public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));
        primaryStage.setTitle("Sportverwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
