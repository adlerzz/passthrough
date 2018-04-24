package by.passthrough.research.utils.jsoner;

public interface Jsonable {
    default String toJSONString(){
        return Jsoner.getInstance().toJSONString(this).trim();
    }
}
