package by.passthrough.research.entities.groups.commands;

/**
 * Created by alst0816 on 14.04.2018
 */
public enum CommandType {
    REQUEST,
    ACTION,
    ANNOUNCEMENT;

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
