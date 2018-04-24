package by.passthrough.research.entities.users;

import by.passthrough.research.utils.jsoner.Jsonable;

/**
 * POJO User
 */
public class User implements Jsonable {

    private long id;
    private String nickname;
    private String nativeName;
    private String internationalName;
    private UserStatus userStatus;

    public User(long id) {
        this.id = id;
        this.userStatus = UserStatus.EXIST;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getInternationalName() {
        return internationalName;
    }

    public void setInternationalName(String internationalName) {
        this.internationalName = internationalName;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}
