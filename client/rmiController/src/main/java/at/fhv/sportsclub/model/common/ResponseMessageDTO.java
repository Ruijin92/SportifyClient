package at.fhv.sportsclub.model.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public @Data class ResponseMessageDTO {

    private List<String> validationMessages;
    private boolean validated;

    private boolean success = false;
    private String contextId = "";
    private String infoMessage = "";

    //public ResponseMessageDTO() {};

}
