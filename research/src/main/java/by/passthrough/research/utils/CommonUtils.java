package by.passthrough.research.utils;

import java.util.Date;

public class CommonUtils {
    private static volatile CommonUtils instance = null;
    private CommonUtils(){}

    public static CommonUtils getInstance() {
        if(instance == null) {
            synchronized (CommonUtils.class) {
                if(instance == null) {
                    instance = new CommonUtils();
                }
            }
        }
        return instance;
    }

    public long getNewId(){
        return (new Date()).getTime();
    }
}
