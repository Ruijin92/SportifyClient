package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.interfacesReturn.IMessageControllerReturn;
import at.fhv.sportsclub.model.message.MessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.jms.Message;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 25.11.2018.
 */

public interface IMessageController extends Remote, IMessageControllerReturn {

    /**
     * Send multiple messages to the queue.
     * @param messages Map<String(Username), String(messageText)>
     */
    void sendMessagesToQueue(SessionDTO sessionDTO, Map<String, String> messages);
    void sendMessageToQueue(SessionDTO sessionDTO, String message, String username);
    void sendMessageToQueue(SessionDTO sessionDTO, String message, String username, String replyTo);
    List<MessageDTO> browseMessagesForUser(SessionDTO sessionDTO, String username);
    boolean removeMessageFromQueueAndArchive(SessionDTO sessionDTO, String correlationID, Boolean confirm);
}
