package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;

public class AuthMessage extends Message {
    private String name;
    private String creds;


    public AuthMessage(){
        this.messageType = MessageType.AUTH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreds() {
        return creds;
    }

    public void setCreds(String creds) {
        this.creds = creds;
    }

    @Override
    public String toJSONString() {
        JSONObject msg = new JSONObject();
        msg.put("type", MessageType.AUTH.name());
        msg.put("name", this.name);
        msg.put("creds", this.creds);
        msg.put("payload", this.payload);
        return msg.toJSONString();
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if(this.getClass() != object.getClass()) return false;

        AuthMessage other = (AuthMessage) object;
        return this.getMessageType().equals(other.getMessageType()) &&
               this.getName().equals(other.getName()) &&
               this.getCreds().equals(other.getCreds()) &&
               this.getPayload().equals(other.getPayload());
    }
}
