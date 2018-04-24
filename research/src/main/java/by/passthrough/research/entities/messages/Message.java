package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.Jsonable;
import by.passthrough.research.utils.jsoner.Jsoner;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public abstract class Message implements Jsonable{
    MessageType messageType;
    Object payload;

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getPayload() {
        return this.payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    /**
     * Method creates concrete appropriate Message object from JSON-string
     * @param json JSON-string which would be converted to message object
     * @return Message object
     */
    public static Message parseFromJSON(String json){
        final Jsoner jsoner = Jsoner.getInstance();
        try {
            JsonObject jsonObject = jsoner.parse(json);
            MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
            return jsoner.parse(json, messageType.getAppropriateClass());
        } catch (JsonSyntaxException e) {
            return new SystemMessage("error", "json parsing error");
        }
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if(this.getClass() != object.getClass()) return false;

        Message message = (Message) object;

        return message.toJSONString().equals(this.toJSONString());
    }
}
