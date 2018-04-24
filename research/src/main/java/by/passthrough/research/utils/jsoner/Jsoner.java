package by.passthrough.research.utils.jsoner;

import by.passthrough.research.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Jsoner {
    private static Jsoner instance = null;
    private static final Logger log = Logger.createLogger(Jsoner.class);
    private JsonParser jsonParser;
    private Gson gson;

    private Jsoner() {
        this.jsonParser = new JsonParser();
        this.gson = new Gson();
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

    public JsonObject parse(String json){
        try {
            return this.jsonParser.parse(json).getAsJsonObject();
        } catch (JsonSyntaxException ex){
            log.error(ex);
            return null;
        }
    }

    public <T> T parse(String json, Class<T> objectClass){
        try {
            return this.gson.fromJson(json, objectClass);
        } catch (JsonSyntaxException ex){
            log.error(ex);
            return null;
        }
    }

    public String toJSONString(Jsonable object){
        return this.gson.toJson(object, object.getClass());
    }

}
