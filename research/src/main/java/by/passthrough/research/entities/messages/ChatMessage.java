package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;

public class ChatMessage extends Message {
    private String dest;

    public ChatMessage(){
        this.messageType = MessageType.CHAT;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public String toJSONString() {
        JSONObject msg = new JSONObject();
        msg.put("type", MessageType.CHAT.name());
        msg.put("dest", this.dest);
        msg.put("payload", this.payload);
        return msg.toJSONString();
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if(this.getClass() != object.getClass()) return false;

        ChatMessage other = (ChatMessage) object;
        return this.getMessageType().equals(other.getMessageType()) &&
               this.getDest().equals(other.getDest()) &&
               this.getPayload().equals(other.getPayload());
    }
}
