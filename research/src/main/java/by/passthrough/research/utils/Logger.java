package by.passthrough.research.utils;

import java.util.Collections;
import java.util.Date;

/**
 * Created by alst0816 on 21.03.2018
 */
public class Logger {

    private Class inClass;
    private boolean debugEnabled = false;
    private boolean warnEnabled = true;

    public static Logger createLogger(Class inClass){
        return new Logger(inClass);
    }

    public static Logger createLogger(Class inClass, boolean debugEnabled){
        Logger newLogger = new Logger(inClass);
        newLogger.setDebugEnabled(debugEnabled);
        return newLogger;
    }

    private Logger(Class inClass){
        this.inClass = inClass;
    }

    private void output(String label, Object object){
        String deep[] = this.inClass.getPackage().getName().split("\\.");

        String methodName = (new Exception()).getStackTrace()[2].getMethodName();
        String objString = object != null ? object.toString(): "";

        System.out.printf("[%1$tF %1$tT] %2$s <%3$s.%4$s#%5$s> - %6$s%n",
                new Date(),
                label,
                deep[deep.length - 1],
                this.inClass.getSimpleName(),
                methodName,
                objString
        );
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public void setDebugEnabled() {
        this.setDebugEnabled(true);
    }

    public boolean isWarnEnabled() {
        return warnEnabled;
    }

    public void setWarnEnabled(boolean warnEnabled) {
        this.warnEnabled = warnEnabled;
    }

    public void debug(Object object){
        if(debugEnabled){
            this.output("DEBUG", object);
        }
    }

    public void info(Object object){
        this.output("INFO ", object);
    }

    public void warn(Object object){
        if(warnEnabled){
            this.output("WARN ", object);
        }
    }

    public void error(Object object){
        this.output("ERROR", object);
    }

    public void println(Object object){
        System.out.println(object.toString());
    }
}
