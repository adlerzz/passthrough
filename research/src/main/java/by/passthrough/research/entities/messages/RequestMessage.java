package by.passthrough.research.entities.messages;


public class RequestMessage extends Message {

    private String command;
    private long id;

    public RequestMessage(long id, String command){
        this.messageType = MessageType.REQUEST;
        this.command = command;
        this.id = id;
        this.payload = null;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

}
