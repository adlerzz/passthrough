package by.passthrough.research.entities.users;

import by.passthrough.research.utils.jsoner.JsonField;
import by.passthrough.research.utils.jsoner.Jsonable;
import by.passthrough.research.utils.jsoner.Jsoner;

/**
 * POJO User
 */
public class User implements Jsonable {

    @JsonField
    private long id;

    @JsonField
    private String nickname;

    @JsonField
    private String nativeName;

    @JsonField
    private String internationalName;

    @JsonField
    private UserStatus userStatus;

    public User() {
        this.id = -1;
        this.userStatus = UserStatus.EXIST;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString(){
        return Jsoner.getInstance().toJSONString(this);
    }
}
