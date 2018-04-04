package by.passthrough.research.entities.messages;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            if(jsonObject.containsKey("messageType")){
                MessageType type = MessageType.valueOf( (String) jsonObject.get("messageType") );
                Message message = type.getInstantiate().get();
                message.fill(jsonObject);
                return message;
            } else {
                return new SystemMessage("error", "no any message type error");
            }

        } catch (ParseException e) {
            return new SystemMessage("error", "json parsing error");
        }
    }

    public void fill(JSONObject jsonObject){
        ArrayList<Field> fields = getFieldsByAnnotationType(JsonField.class);

        for(Field field: fields){

            boolean flag = field.isAccessible();
            JsonField annotation = field.getAnnotation(JsonField.class);
            field.setAccessible(true);
            try {
                Object value;
                if(annotation.key().isEmpty()) {
                    value = jsonObject.get(field.getName());
                } else {
                    value = jsonObject.get(annotation.key());
                }

                if(MessageType.class.equals(field.getType())){
                    value = MessageType.valueOf(value.toString());
                }

                field.set(this, value);

            } catch (IllegalAccessException ignored) {}
            field.setAccessible(flag);
        }
    }

    @Override
    public String toString(){
        JSONObject msg = new JSONObject();
        ArrayList<Field> fields = getFieldsByAnnotationType(JsonField.class);

        for(Field field: fields){
            boolean flag = field.isAccessible();
            JsonField annotation = field.getAnnotation(JsonField.class);
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                String key;
                if(value != null) {
                    if(annotation.key().isEmpty()) {
                        key = field.getName();
                    } else {
                        key = annotation.key();
                    }
                    msg.put(key, value);
                }
            } catch (IllegalAccessException ignored) {}
            field.setAccessible(flag);
        }

        return msg.toJSONString();
    }

    private ArrayList<Field> getFieldsByAnnotationType(Class<? extends Annotation> annotationType){
        Class msgClass = this.getClass();
        Class superClass = msgClass.getSuperclass();

        Stream<Field> parentFields;
        Stream<Field> ownFields;

        if(superClass != null) {
            parentFields = Stream.of(superClass.getDeclaredFields());
        } else {
            parentFields = Stream.empty();
        }
        ownFields = Stream.of(msgClass.getDeclaredFields());

        return Stream.concat(parentFields, ownFields)
                .filter( field -> field.isAnnotationPresent(annotationType) )
                .collect(Collectors.toCollection(ArrayList::new));

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
