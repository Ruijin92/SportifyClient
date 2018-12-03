package at.fhv.team2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.jms.Message;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

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


        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        primaryStage.setTitle("Sportverwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        Thread synchronMessages = new Thread(() -> {
            boolean isRunning = true;
            while(isRunning) {
                List<Message> messages = DataProvider.
                        getMessageControllerInstance().
                        browseMessagesForUser(DataProvider.getSession(), "");
                if(messages.size() > 0) {
                    DataProvider.setMessageStatus("New Messages");
                } else {
                    DataProvider.setMessageStatus("Messages");
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        synchronMessages.setDaemon(true);
        synchronMessages.start();
    }
}
