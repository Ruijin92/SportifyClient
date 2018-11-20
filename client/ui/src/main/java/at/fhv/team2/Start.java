package at.fhv.team2;

import at.fhv.sportsclub.controller.interfaces.IAuthenticationController;
import at.fhv.sportsclub.factory.IControllerFactory;
import at.fhv.sportsclub.model.security.SessionDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Uray Örnek on 11/5/2018.
 */
public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        Registry registry = LocateRegistry.getRegistry(1099);
        DataProvider.setRegistry(registry);

       /* IControllerFactory controllerFactory = (IControllerFactory) registry.lookup("ControllerFactory");
        IAuthenticationController authenticationController = controllerFactory.getAuthenticationController();

        SessionDTO session = authenticationController.authenticate(pw, pw.toCharArray());
        DataProvider.setSession(session);
*/
        String pw = "snoop@do.gg";

        DataProvider dataProvider = DataProvider.get();
        dataProvider.authenticate("snoop@do.gg", pw.toCharArray());

        Parent root = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));
        primaryStage.setTitle("Sportverwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
