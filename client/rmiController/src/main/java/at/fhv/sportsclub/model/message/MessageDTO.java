package at.fhv.sportsclub.model.message;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Alex on 04.12.2018.
 */
@AllArgsConstructor
public @Data class MessageDTO implements Serializable {
    public MessageDTO() {}

    private static final long serialVersionUID = 4555941810384565028L;

    private String username;
    private String body;
    private String replyTo;

    private String correlationsId;

    private ResponseMessageDTO response = null;
}
