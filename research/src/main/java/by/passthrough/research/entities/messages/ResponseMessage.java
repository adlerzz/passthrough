package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class ResponseMessage extends Message {

    @JsonField
    private long id;

    public ResponseMessage(){
        this.messageType = MessageType.RESPONSE;
        this.id = -1;
        this.payload = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
