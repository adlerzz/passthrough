package by.passthrough.research.entities.messages;

import by.passthrough.research.entities.users.User;
import by.passthrough.research.utils.jsoner.JsonField;

public class AuthMessage extends Message {

    @JsonField
    private long id;

    @JsonField
    private User user;

    @JsonField
    private String creds;


    public AuthMessage(){
        this.messageType = MessageType.AUTH;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreds() {
        return creds;
    }

    public void setCreds(String creds) {
        this.creds = creds;
    }

}
