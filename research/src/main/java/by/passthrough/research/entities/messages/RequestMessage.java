package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class RequestMessage extends Message {

    @JsonField
    private String command;

    @JsonField
    private long id;

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public RequestMessage(){
        this(-1, null);
    }

    public RequestMessage(long id, String command){
        this.messageType = MessageType.REQUEST;
        this.command = command;
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

}
