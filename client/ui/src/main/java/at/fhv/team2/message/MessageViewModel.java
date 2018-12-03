package at.fhv.team2.message;

import javafx.collections.ObservableList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.jms.Message;
import java.util.List;

/**
 * Created by Alex on 26.11.2018.
 */
@NoArgsConstructor
public @Data class MessageViewModel {

    private ObservableList<Message> messages;

    public void addToMessages(List<Message> addMessages) {
        messages.addAll(addMessages);
    }

}
