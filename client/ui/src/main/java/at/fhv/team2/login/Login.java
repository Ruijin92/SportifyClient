package at.fhv.team2.login;

import at.fhv.team2.DataProvider;
import at.fhv.team2.roles.Permission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    public TextField username;
    public TextField password;
    public BorderPane pane;

    private ValidationSupport validationSupport = new ValidationSupport();

    /**
     * Just for Developing purpose
     *
     * @param event
     * @throws IOException
     */
    public void logginAsGuest(MouseEvent event) throws IOException {
        Permission.getPermission().loadAdmin();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Just for Developing purpose
     *
     * @param event
     * @throws IOException
     */
    public void logginAsAdmin(MouseEvent event) throws IOException, NotBoundException {
        DataProvider dataProvider = DataProvider.get();

        String pw = "snoop@do.gg";
        if (dataProvider.authenticate("snoop@do.gg", pw.toCharArray()).equals("")) {

            Permission.getPermission().setRoles(DataProvider.getSession().getRoles());
            Permission.getPermission().checkPermission();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setContentText("Username or Password wrong - try again.");
            alert.showAndWait();
            username.setText("");
            password.setText("");
        }
        //Permission.getPermission().loadAdmin();
    }

    public void loginfunction(ActionEvent actionEvent) throws IOException, NotBoundException {

        if (!validationSupport.isInvalid()) {
            DataProvider dataProvider = DataProvider.get();

            if (dataProvider.authenticate(username.getText(), password.getText().toCharArray()).equals("")) {

                Permission.getPermission().setRoles(DataProvider.getSession().getRoles());
                Permission.getPermission().checkPermission();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login failed");
                alert.setContentText("Username or Password wrong - try again.");
                alert.showAndWait();
                username.setText("");
                password.setText("");
            }
        } else {
            Notifications.create().graphic(pane).text(validationSupport.getValidationResult().getMessages().toString()).showWarning();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validationSupport.registerValidator(username, Validator.createEmptyValidator("Username - Has to be filled"));
        validationSupport.registerValidator(password, Validator.createEmptyValidator("Password - Has to be filled"));
    }
}
