package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Message implements Jsonable{

    @JsonField
    protected MessageType messageType;

    @JsonField
    protected Object payload;

    public Message(){

    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getPayload() {
        return this.payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public static Message parseFromJSON(String json){
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            if(jsonObject.containsKey("messageType")){
                MessageType type = MessageType.valueOf( (String) jsonObject.get("messageType") );
                Message message = type.getInstantiate().get();
                Jsoner.getInstance().fillObject(jsonObject, message);
                return message;
            } else {
                return new SystemMessage("error", "no any message type error");
            }

        } catch (ParseException e) {
            return new SystemMessage("error", "json parsing error");
        }
    }

    @Override
    public String toString(){
        return Jsoner.getInstance().toJSONString(this);
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if(this.getClass() != object.getClass()) return false;

        Message message = (Message) object;

        return message.toString().equals(this.toString());
    }
}
