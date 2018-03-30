package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;

public class RequestMessage extends Message {
    private String command;
    private String id;

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public RequestMessage(){
        this.messageType = MessageType.REQUEST;
        this.command = null;
        this.id = null;
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

    @Override
    public String toJSONString() {
        JSONObject msg = new JSONObject();
        msg.put("type", MessageType.REQUEST.name());
        msg.put("command", this.command);
        msg.put("id", this.id);
        msg.put("payload", this.payload);
        return msg.toJSONString();
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if(this.getClass() != object.getClass()) return false;

        RequestMessage other = (RequestMessage) object;
        return this.getMessageType().equals(other.getMessageType()) &&
               this.getCommand().equals(other.getCommand()) &&
               this.getId().equals(other.getId()) &&
               this.getPayload().equals(other.getPayload());
    }

}
