package at.fhv.sportsclub.controller.interfaces;

import javax.jms.Message;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 25.11.2018.
 */

public interface IMessageController extends Remote {

    /**
     * Send multiple messages to the queue.
     * @param messages Map<String(Username), String(messageText)>
     */
    void sendMessagesToQueue(Map<String, String> messages);// sendMessagesToQueue(List<Message>, List<User>)
    void sendMessageToQueue(String message, String username);
    List<Message> browseMessagesForUser(String username); // (UserString für messageSelektor); //With Browser
    boolean deleteMessageFromQueue(String correlationID);
    boolean deleteMessagesFromQueue(List<String> correlationIDs);

}
