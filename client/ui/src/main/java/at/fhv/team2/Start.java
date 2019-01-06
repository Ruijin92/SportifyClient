package at.fhv.team2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
/**
 * Created by Uray Örnek on 11/5/2018.
 */
public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        InitialContext ctx = new InitialContext(env);

        DataProviderFactory.setInitialContext(ctx);

        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        primaryStage.setTitle("Sportverwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
