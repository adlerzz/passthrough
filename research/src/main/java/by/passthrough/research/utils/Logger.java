package by.passthrough.research.utils;

import by.passthrough.research.utils.configurator.Configurator;
import by.passthrough.research.utils.configurator.LoadConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class Logger {

    private Class inClass;

    @LoadConfig(name="DEBUG")
    private boolean debugEnabled = false;

    @LoadConfig(name = "WARN")
    private boolean warnEnabled = true;

    public static Logger createLogger(Class inClass){
        return new Logger(inClass);
    }

    private Logger(Class inClass){
        this.inClass = inClass;
        Configurator.getInstance().configure(this);
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
        if(object instanceof Exception) {
            Exception ex = (Exception) object;

            this.output("ERROR", ex.getLocalizedMessage() + "\n" + String.join("\n", Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())) );
        }
    }

    public void println(Object object){
        System.out.println(object.toString());
    }
}
