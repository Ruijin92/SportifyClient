package at.fhv.team2.message;

import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.team2.DataProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javax.jms.Message;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

/**
 * Created by Alex on 26.11.2018.
 */

public class MessageModel implements Initializable {

    public TableView messageTable;
    public TextArea messageBody;
    public Button confirmMatch, cancelMatch;

    private MessageViewModel messageViewModel;

    public MessageModel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Message.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageViewModel = new MessageViewModel();
        messageViewModel.setMessages(FXCollections.observableArrayList());
        messageTable.setItems(messageViewModel.getMessages());

        // Lade alle Nachrichten
        IMessageController messageControllerInstance = DataProvider.getMessageControllerInstance();
        Thread thread = new Thread(() ->
            messageViewModel.addToMessages(
                messageControllerInstance.browseMessagesForUser(
                    DataProvider.getSession(), DataProvider.getSession().getMyUserId()
            )
        ));
        thread.setDaemon(true);
        thread.start();
    }

    public void openMessage() {
        // if(Prüfen ob selectedItem replyTo null ist) {}
        selectedItem = "";
        DataProvider.getMessageControllerInstance().removeMessageFromQueueAndArchive(DataProvider.getSession(), )

        messageBody.setText(); // Text setzen von dem selected Item
    }

    public void confirmMessage() {
        DataProvider.getMessageControllerInstance().
    }

    public void cancelMessage() {

    }
}
