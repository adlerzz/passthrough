package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class ResponseMessage extends Message {

    @JsonField
    private String id;

    public ResponseMessage(){
        this.messageType = MessageType.RESPONSE;
        this.id = null;
        this.payload = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
