package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;

public class SystemMessage extends Message {

    @JsonField
    private String subtype;

    public SystemMessage(){
        this.messageType = MessageType.SYSTEM;
        this.subtype = null;
        this.payload = null;
    }

    public SystemMessage(String subtype, String payload){
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

//    @Override
//    public String toJSONString() {
//        JSONObject msg = new JSONObject();
//        msg.put("type", MessageType.SYSTEM.name());
//        msg.put("subtype", this.subtype);
//        msg.put("payload", this.payload);
//        return msg.toJSONString();
//    }

    public static final SystemMessage STOP = new SystemMessage("signal", "stop");

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if(this.getClass() != object.getClass()) return false;

        SystemMessage other = (SystemMessage) object;
        return this.getMessageType().equals(other.getMessageType()) &&
               this.getSubtype().equals(other.getSubtype()) &&
               this.getPayload().equals(other.getPayload());
    }

}
