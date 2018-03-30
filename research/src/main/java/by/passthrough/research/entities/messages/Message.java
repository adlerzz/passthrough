package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Message {
    protected MessageType messageType;
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
            if(msg.containsKey("type")){
                MessageType type = MessageType.valueOf( (String) msg.get("type") );
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

                    default:
                        SystemMessage error = new SystemMessage();
                        error.setSubtype("error");
                        error.setPayload("unknown message type error");
                        return error;
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

    public abstract String toJSONString();

}
