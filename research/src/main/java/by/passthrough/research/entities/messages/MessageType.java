package by.passthrough.research.entities.messages;

public enum MessageType {
    AUTH(AuthMessage.class),
    REQUEST(RequestMessage.class),
    RESPONSE(ResponseMessage.class),
    CHAT(ChatMessage.class),
    GROUP(GroupMessage.class),
    SYSTEM(SystemMessage.class);

    private Class appropriateClass;

    public <T> Class<T> getAppropriateClass() {
        return appropriateClass;
    }

    MessageType(Class appropriateClass){
        this.appropriateClass = appropriateClass;
    }

}
