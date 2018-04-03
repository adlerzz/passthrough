package by.passthrough.research;

import by.passthrough.research.engine.ai.AnswerSolver;
import by.passthrough.research.engine.server.HostThread;
import by.passthrough.research.entities.messages.AuthMessage;
import by.passthrough.research.entities.messages.ChatMessage;
import by.passthrough.research.entities.messages.Message;
import by.passthrough.research.entities.messages.MessageType;
import by.passthrough.research.entities.messages.RequestMessage;
import by.passthrough.research.entities.messages.ResponseMessage;
import by.passthrough.research.entities.messages.SystemMessage;

public class CustomHostThread extends HostThread {
    private static final AnswerSolver answerSolver = AnswerSolver.getInstance();
    public CustomHostThread(){
        super();
    }

    @Override
    public void prepare() throws Exception {
        AuthMessage msg = (AuthMessage) Message.parseFromJSON(this.receive());
        this.put("id", msg.getName());
        this.getConnectionsManager().addNamedThread(msg.getName(), this);

    }

    @Override
    public void doAction() throws Exception {
        Message msg = Message.parseFromJSON(this.receive());
        if(SystemMessage.STOP.equals(msg)){
            this.stop = true;
            this.send(this.receive());
        } else {

            MessageType type = msg.getMessageType();
            switch (type){
                case CHAT:{
                    ChatMessage chatMessage = (ChatMessage) msg;
                    HostThread destThread = this.getConnectionsManager().getThreadById(chatMessage.getDest());
                    destThread.send(chatMessage.toString());
                } break;

                case REQUEST: {
                    RequestMessage requestMessage = (RequestMessage) msg;
                    ResponseMessage responseMessage = answerSolver.handleRequest(this.getConnectionsManager(), requestMessage);
                    this.send(responseMessage.toString());
                } break;

                case SYSTEM:{
                    SystemMessage systemMessage = (SystemMessage) msg;
                } break;
            }
        }
    }
}
