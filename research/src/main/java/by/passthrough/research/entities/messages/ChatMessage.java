package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class ChatMessage extends Message {

    @JsonField
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

}
