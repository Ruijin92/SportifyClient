package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.message.MessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
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
    void sendMessagesToQueue(SessionDTO sessionDTO, Map<String, String> messages) throws RemoteException;
    void sendMessageToQueue(SessionDTO sessionDTO, String message, String username) throws RemoteException;
    void sendMessageToQueue(SessionDTO sessionDTO, String message, String username, String replyTo) throws RemoteException;
    List<MessageDTO> browseMessagesForUser(SessionDTO sessionDTO, String username) throws RemoteException;
    boolean removeMessageFromQueueAndArchive(SessionDTO sessionDTO, String correlationID, Boolean confirm) throws RemoteException;

}
