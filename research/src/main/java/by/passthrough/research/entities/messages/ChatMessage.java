package by.passthrough.research.entities.messages;

public class ChatMessage extends Message {
    private long src;
    private long dest;

    public ChatMessage(long src, long dest, Object payload) {
        this.messageType = MessageType.CHAT;
        this.src = src;
        this.dest = dest;
        this.payload = payload;
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
