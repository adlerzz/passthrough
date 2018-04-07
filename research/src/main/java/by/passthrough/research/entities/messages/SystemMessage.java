package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class SystemMessage extends Message {

    @JsonField
    private String subtype;

    public SystemMessage(){
        this(null, null);
    }

    public SystemMessage(String subtype, Object payload){
        this.messageType = MessageType.SYSTEM;
        this.subtype = subtype;
        this.payload = payload;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public static final SystemMessage STOP = new SystemMessage("signal", "stop");

}
