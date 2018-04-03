package by.passthrough.research.entities.messages;

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
