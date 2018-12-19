package at.fhv.sportsclub.ejb.interfaces;

import at.fhv.sportsclub.interfacesReturn.IMessageControllerReturn;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Remote;
import java.util.Map;

/**
 * Created by Alex on 25.11.2018.
 */

@Remote
public interface IMessageController extends IMessageControllerReturn {

    /**
     * Send multiple messages to the queue.
     * @param messages Map<String(Username), String(messageText)>
     */
    void sendMessagesToQueue(SessionDTO sessionDTO, Map<String, String> messages);
    void sendMessageToQueue(SessionDTO sessionDTO, String message, String username);
    void sendMessageToQueue(SessionDTO sessionDTO, String message, String username, String replyTo);
    //List<Message> browseMessagesForUser(SessionDTO sessionDTO, String username);
    boolean removeMessageFromQueueAndArchive(SessionDTO sessionDTO, String correlationID, String replyMessage);

}
