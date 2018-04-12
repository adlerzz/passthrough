package by.passthrough.research.utils.jsoner;

import by.passthrough.research.utils.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alst0816 on 06.04.2018
 */
public class Jsoner {
    private static Jsoner instance = null;
    private static final Logger log = Logger.createLogger(Jsoner.class);

    private Jsoner() {

    }

    public static Jsoner getInstance() {
        if(instance == null){
            synchronized (Jsoner.class) {
                if(instance == null) {
                    instance = new Jsoner();
                }
            }
        }
        return instance;
    }

    public void fillObject(JSONObject jsonObject, Jsonable object){
        ArrayList<Field> fields = getJsonFields(object.getClass());

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

                Class fieldType = field.getType();

                if(fieldType.isEnum()){
                    value = Enum.valueOf((Class<? extends Enum>)fieldType, value.toString());
                }

                if(Arrays.stream(fieldType.getInterfaces()).anyMatch(Jsonable.class::equals)){
                    Jsonable jsonableObject = (Jsonable)fieldType.newInstance();

                    JSONObject jsonValue = (JSONObject) new JSONParser().parse(value.toString());
                    fillObject(jsonValue, jsonableObject);
                    value = jsonableObject;
                }

                field.set(object, value);

            } catch (IllegalAccessException | InstantiationException | ParseException e) {
                log.error(e);
            }
            field.setAccessible(flag);
        }
    }

    public String toJSONString(Jsonable object){
        JSONObject msg = new JSONObject();
        ArrayList<Field> fields = getJsonFields(object.getClass());

        for(Field field: fields){
            boolean flag = field.isAccessible();
            JsonField annotation = field.getAnnotation(JsonField.class);
            field.setAccessible(true);
            try {
                Object value = field.get(object);
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

    private ArrayList<Field> getJsonFields(Class<? extends Jsonable> jsonableClass){

        Class superClass = jsonableClass.getSuperclass();

        Stream<Field> parentFields;
        Stream<Field> ownFields;

        if(superClass != null) {
            parentFields = Stream.of(superClass.getDeclaredFields());
        } else {
            parentFields = Stream.empty();
        }
        ownFields = Stream.of(jsonableClass.getDeclaredFields());

        return Stream.concat(parentFields, ownFields)
                .filter( field -> field.isAnnotationPresent(JsonField.class) )
                .collect(Collectors.toCollection(ArrayList::new));

    }
}
