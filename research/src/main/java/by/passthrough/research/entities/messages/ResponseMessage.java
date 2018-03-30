package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;

public class ResponseMessage extends Message {
    private String id;

    public ResponseMessage(){
        this.messageType = MessageType.RESPONSE;
        this.id = null;
        this.payload = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toJSONString() {
        JSONObject msg = new JSONObject();
        msg.put("type", MessageType.RESPONSE.name());
        msg.put("id", this.id);
        msg.put("payload", this.payload);
        return msg.toJSONString();
    }
}
