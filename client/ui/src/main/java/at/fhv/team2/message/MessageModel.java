package at.fhv.team2.message;

import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.team2.DataProvider;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alex on 26.11.2018.
 */

public class MessageModel extends AnchorPane implements Initializable {

    public TableView messageTable;
    public TextArea messageBody;
    public Button agreeButton, rejectButton;
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
                    DataProvider.getSession(), "Alex"/*DataProvider.getSession().getMyUserId()*/ //TODO
            )
        ));
        thread.setDaemon(true);
        thread.start();

    }

    public void openMessage(MouseEvent event) {
        TextMessage selectedMessage = (TextMessage) messageTable.getSelectionModel().getSelectedItem();
        if(selectedMessage == null) { return; }

        try {
            messageBody.setText(selectedMessage.getText());

            if(selectedMessage.getStringProperty("replyTo") == null) {
                DataProvider.
                        getMessageControllerInstance().
                        removeMessageFromQueueAndArchive(DataProvider.getSession(), selectedMessage.getJMSCorrelationID(), null);
                agreeButton.setVisible(false);
                rejectButton.setVisible(false);
            } else {
                agreeButton.setVisible(true);
                rejectButton.setVisible(true);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //TODO Message
    public void confirmMessage(ActionEvent event) {
        replyToMessage("Ich stimme zu");
    }

    public void cancelMessage(ActionEvent event) {
        replyToMessage("Ich lehne ab");
    }

    private void replyToMessage(String replyMessageText) {
        TextMessage selectedMessage = (TextMessage) messageTable.getSelectionModel().getSelectedItem();
        if(selectedMessage == null) { return; }

        try {
            DataProvider.
                    getMessageControllerInstance().
                    removeMessageFromQueueAndArchive(DataProvider.getSession(), selectedMessage.getJMSCorrelationID(), replyMessageText);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
