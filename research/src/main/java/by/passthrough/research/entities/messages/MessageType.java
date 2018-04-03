package by.passthrough.research.entities.messages;

public enum MessageType {
    AUTH,
    REQUEST,
    RESPONSE,
    CHAT,
    GROUP,
    SYSTEM;

    @Override
    public String toString(){
        return "\"" + name() + "\"";
    }
}
