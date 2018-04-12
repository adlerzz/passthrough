package by.passthrough.research.entities.users;

/**
 * Created by alst0816 on 12.04.2018
 */
public enum UserStatus {
    ONLINE,
    DO_NOT_DISTURB,
    AWAY_FROM_KEYBOARD,
    OFFLINE,
    EXIST;

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
