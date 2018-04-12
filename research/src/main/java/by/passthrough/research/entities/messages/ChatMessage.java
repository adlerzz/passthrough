package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class ChatMessage extends Message {

    @JsonField
    private long src;

    @JsonField
    private long dest;

    public ChatMessage(){
        this.messageType = MessageType.CHAT;
    }

    public long getSrc() {
        return src;
    }

    public void setSrc(long src) {
        this.src = src;
    }

    public long getDest() {
        return dest;
    }

    public void setDest(long dest) {
        this.dest = dest;
    }

}
