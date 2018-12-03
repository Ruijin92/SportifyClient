package at.fhv.team2.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Alex on 26.11.2018.
 */
@AllArgsConstructor
public @Data class MessageViewModel {

    private List<Message> messages;

}
