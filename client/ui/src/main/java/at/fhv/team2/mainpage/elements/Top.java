package at.fhv.team2.mainpage.elements;

import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.interfacesReturn.IPersonControllerReturn;
import at.fhv.sportsclub.model.message.MessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.team2.DataProviderFactory;
import at.fhv.team2.IDataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.roles.Permission;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import javax.naming.NamingException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class Top extends HBox implements Initializable {

    public Label username;
    public Label siteName;
    public Button messageButton;
    private int messageCounter;
    private IDataProvider dataProvider;

    private IPersonControllerReturn personController;

    public Top() {
        dataProvider = DataProviderFactory.getCurrentDataProvider();

        this.personController = dataProvider.getPersonControllerInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Top.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logoutUser(ActionEvent event) throws IOException, NotBoundException, NamingException {
        dataProvider.logout();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PersonDTO person = null;
        try {
            person = this.personController.getEntryDetails(dataProvider.getSession(), dataProvider.getSession().getMyUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(person.getFirstName() == null){
            username.setText("Admin");
        } else {
            username.setText(person.getFirstName());
        }
        siteName.setText("DASHBOARD");

        username.setText(Permission.getPermission().getUsername());
        dataProvider.setMessageStatus("Nachrichten");
        messageButton.textProperty().bind(dataProvider.getMessageStatus());

        new Thread(() -> {
            while(dataProvider.getSession() != null) {
                List<MessageDTO> messageDTOS = null;
                try {
                    messageDTOS =
                            dataProvider.
                                    getMessageControllerInstance().
                                    browseMessagesForUser(null, dataProvider.getSession().getMyUserId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                String messageText;
                if(messageDTOS != null && messageDTOS.size() > 0) {
                    messageText = "Neue Nachrichten";
                } else {
                    messageText = "Nachrichten";
                }

                Platform.runLater(() -> dataProvider.setMessageStatus(messageText));

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setSiteName(String name){
        siteName.setText(name);
    }

    public void switchMessage(ActionEvent event) {
        PageProvider.getPageProvider().switchMessages();
    }

    public void setMessageCount(int count){
        messageCounter = messageCounter + count;
        messageButton.setText(messageCounter + "new Messages");
    }
}
