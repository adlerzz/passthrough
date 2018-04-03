package by.passthrough.research.entities.messages;

import java.util.function.Supplier;

public enum MessageType {
    AUTH(AuthMessage::new),
    REQUEST(RequestMessage::new),
    RESPONSE(ResponseMessage::new),
    CHAT(ChatMessage::new),
    GROUP(SystemMessage::new),
    SYSTEM(SystemMessage::new);

    private Supplier<Message> instantiate;

    public Supplier<Message> getInstantiate() {
        return instantiate;
    }

    MessageType(Supplier<Message> instantiate){
        this.instantiate = instantiate;
    }

    @Override
    public String toString(){
        return "\"" + name() + "\"";
    }


}
