package by.passthrough.research.entities.messages;

import by.passthrough.research.entities.users.User;

public class AuthMessage extends Message {

    private long id;
    private User user;
    private String creds;

    public AuthMessage(long id, User user) {
        this.messageType = MessageType.AUTH;
        this.id = id;
        this.user = user;
        this.creds = null;
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
