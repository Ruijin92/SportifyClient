package at.fhv.team2.message;

import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.sportsclub.model.message.MessageDTO;
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

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
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
        Thread thread = new Thread(() -> {
            try {
                List<MessageDTO> messageDTOS =
                        messageControllerInstance.
                                browseMessagesForUser(
                                        DataProvider.getSession(),
                                        DataProvider.getSession().getMyUserId());
                for(MessageDTO actual: messageDTOS) {
                    if(actual.getReplyTo() == null) {
                        actual.setReplyTo("System");
                    }
                }
                messageViewModel.addToMessages(messageDTOS);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void openMessage(MouseEvent event) {
        MessageDTO selectedMessage = (MessageDTO) messageTable.getSelectionModel().getSelectedItem();
        if(selectedMessage == null) { return; }

        try {
            messageBody.setText(selectedMessage.getBody());

            if(selectedMessage.getReplyTo() == null || selectedMessage.getReplyTo().equals("System")) {
                DataProvider.
                        getMessageControllerInstance().
                        removeMessageFromQueueAndArchive(DataProvider.getSession(), selectedMessage.getCorrelationsId(), null);
                agreeButton.setVisible(false);
                rejectButton.setVisible(false);
            } else {
                agreeButton.setVisible(true);
                rejectButton.setVisible(true);
            }
        } catch (RemoteException e) {
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
        MessageDTO selectedMessage = (MessageDTO) messageTable.getSelectionModel().getSelectedItem();
        if(selectedMessage == null) { return; }

        try {
            DataProvider.
                    getMessageControllerInstance().
                    removeMessageFromQueueAndArchive(DataProvider.getSession(), selectedMessage.getCorrelationsId(), replyMessageText);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
