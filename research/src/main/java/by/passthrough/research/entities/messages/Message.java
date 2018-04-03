package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Message {

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
            JSONObject msg = (JSONObject) jsonParser.parse(json);
            if(msg.containsKey("messageType")){
                MessageType type = MessageType.valueOf( (String) msg.get("messageType") );
                switch (type){
                    case AUTH: {
                        AuthMessage res = new AuthMessage();
                        res.setName((String) msg.get("name"));
                        res.setCreds((String) msg.get("creds"));
                        res.setPayload( msg.get("payload"));
                        return res;
                    }

                    case CHAT: {
                        ChatMessage res = new ChatMessage();
                        res.setDest((String) msg.get("dest"));
                        res.setPayload( msg.get("payload"));
                        return res;
                    }

                    case REQUEST:{
                        RequestMessage res = new RequestMessage();
                        res.setCommand((String) msg.get("command"));
                        res.setId((String) msg.get("id"));
                        res.setPayload( msg.get("payload"));
                        return res;
                    }

                    case RESPONSE:{
                        ResponseMessage res = new ResponseMessage();
                        res.setId((String) msg.get("id"));
                        res.setPayload( msg.get("payload"));
                        return res;
                    }

                    case SYSTEM: {
                        SystemMessage res = new SystemMessage();
                        res.setSubtype((String) msg.get("subtype"));
                        res.setPayload( msg.get("payload"));
                        return res;
                    }

                    default: {
                        SystemMessage error = new SystemMessage();
                        error.setSubtype("error");
                        error.setPayload("unknown message type error");
                        return error;
                    }
                }
            } else {
                SystemMessage error = new SystemMessage();
                error.setSubtype("error");
                error.setPayload("no any message type error");
                return error;
            }

        } catch (ParseException e) {
            SystemMessage error = new SystemMessage();
            error.setSubtype("error");
            error.setPayload("json parsing error");
            return error;
        }
    }

    @Override
    public String toString(){
        JSONObject msg = new JSONObject();

        ArrayList<Field> fields = new ArrayList<>();

        Class msgClass = this.getClass();
        Class superClass = msgClass.getSuperclass();
        if(superClass != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(superClass.getDeclaredFields())));
        }
        fields.addAll(new ArrayList<>(Arrays.asList(msgClass.getDeclaredFields())));

        for(Field field: fields){
            if(field.isAnnotationPresent(JsonField.class)) {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if(value != null) {
                        msg.put(field.getName(), value);
                    }
                } catch (IllegalAccessException ignored) {}
                field.setAccessible(flag);
            }
        }

        return msg.toJSONString();
    }
}
