package at.fhv.team2.login;

import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.roles.Permission;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public TextField username;
    public TextField password;
    public BorderPane pane;

    private ValidationSupport validationSupport = new ValidationSupport();

    public void logginAsGuest(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void loginfunction(ActionEvent actionEvent) throws IOException {

        if (!validationSupport.isInvalid()) {
            String pw = "snoop@do.gg";
            //TODO: test if this would work
            //DataProvider dataProvider = DataProvider.get();
            //dataProvider.authenticate(pw, pw.toCharArray()); //FIXME standart
            //dataProvider.authenticate(password.getText(), password.getText().toCharArray());

            //TODO: A Factory for Permission or static, because we have to keep the information
            List<RoleDTO> roleList = new ArrayList<>();
            Permission permission = new Permission();
            permission.setRoles(roleList);
            permission.checkPermission();

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
