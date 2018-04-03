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
