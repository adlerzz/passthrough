package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class RequestMessage extends Message {

    @JsonField
    private String command;

    @JsonField
    private String id;

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public RequestMessage(){
        this(null, null);
    }

    public RequestMessage(String id, String command){
        this.messageType = MessageType.REQUEST;
        this.command = command;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
