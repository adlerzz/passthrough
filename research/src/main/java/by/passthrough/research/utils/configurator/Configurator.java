package by.passthrough.research.utils.configurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by alst0816 on 02.04.2018
 */
public final class Configurator {
    private static volatile Configurator instance = null;
    private Properties properties;

    private Configurator(){
        this.properties = new Properties();
        try(FileInputStream fis = new FileInputStream("main.config")){
            this.properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configurator getInstance() {
        if(instance == null) {
            synchronized (Configurator.class) {
                if(instance == null) {
                    instance = new Configurator();
                }
            }
        }
        return instance;
    }

    public void configure(Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                LoadConfig annotation = field.getAnnotation(LoadConfig.class);
                if(annotation != null) {
                    String value = this.properties.getProperty(annotation.name(), annotation.defaultValue());

                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    field.set(object, value);
                    field.setAccessible(flag);
                }
            }
        } catch (IllegalAccessException ignored){
            ignored.printStackTrace();
        }
    }


}
