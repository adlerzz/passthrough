package by.passthrough.research;

import by.passthrough.research.engine.ai.AnswerSolver;
import by.passthrough.research.engine.server.HostThread;
import by.passthrough.research.entities.messages.*;

public class CustomHostThread extends HostThread {
    private static final AnswerSolver answerSolver = AnswerSolver.getInstance();
    public CustomHostThread(){
        super();
    }

    @Override
    public void prepare() throws Exception {
        AuthMessage msg = (AuthMessage) Message.parseFromJSON(this.receive());
        this.put("id", msg.getId());
        this.put("user", msg.getUser());
        this.getConnectionsManager().addNamedThread(msg.getId(), this);

    }

    @Override
    public void doAction() throws Exception {
        String recv = this.receive();
        Message msg = Message.parseFromJSON(recv);
        if(SystemMessage.STOP.equals(msg)){
            this.stop = true;
            this.send(recv);
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
                    ResponseMessage responseMessage = answerSolver.handleRequest(this, requestMessage);
                    this.send(responseMessage.toString());
                } break;

                case SYSTEM:{
                    SystemMessage systemMessage = (SystemMessage) msg;
                } break;
            }
        }
    }
}
