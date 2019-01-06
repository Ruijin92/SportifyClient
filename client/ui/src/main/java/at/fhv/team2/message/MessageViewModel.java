package at.fhv.team2.message;

import at.fhv.sportsclub.model.message.MessageDTO;
import javafx.collections.ObservableList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.jms.Message;
import java.util.List;

/**
 * Created by Alex on 26.11.2018.
 */
@NoArgsConstructor
public @Data class MessageViewModel {

    private ObservableList<MessageDTO> messages;

    public void addToMessages(List<MessageDTO> addMessages) {
        messages.addAll(addMessages);
    }

}
