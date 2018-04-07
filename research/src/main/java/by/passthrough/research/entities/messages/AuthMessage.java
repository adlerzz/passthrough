package by.passthrough.research.entities.messages;

import by.passthrough.research.utils.jsoner.JsonField;

public class AuthMessage extends Message {

    @JsonField
    private String name;

    @JsonField
    private String creds;


    public AuthMessage(){
        this.messageType = MessageType.AUTH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreds() {
        return creds;
    }

    public void setCreds(String creds) {
        this.creds = creds;
    }

}
