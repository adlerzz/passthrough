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
import by.passthrough.research.utils.Logger;
import org.json.simple.JSONArray;

public class CustomHostThread extends HostThread {
    private static Logger log = Logger.createLogger(CustomHostThread.class, true);
    private static final AnswerSolver answerSolver = AnswerSolver.getInstance();
    public CustomHostThread(){
        super();
    }

    @Override
    public void prepare() throws Exception {
        String recv = this.receive();

        AuthMessage msg = (AuthMessage) Message.parseFromJSON(recv);
        this.put("id", msg.getName());
        this.getConnectionsManager().addNamedThread(msg.getName(), this);

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
            switch (type)
            {
                case CHAT:{
                    ChatMessage chatMessage = (ChatMessage) msg;
                    HostThread destThread = this.getConnectionsManager().getThreadById(chatMessage.getDest());
                    destThread.send(chatMessage.toJSONString());
                    break;
                }

                case REQUEST: {
                    RequestMessage requestMessage = (RequestMessage) msg;
                    ResponseMessage responseMessage = answerSolver.handleRequest(this.getConnectionsManager(), requestMessage);
                    this.send(responseMessage.toJSONString());
                    break;
                }

                case SYSTEM:{
                    SystemMessage systemMessage = (SystemMessage) msg;
                } break;

            }

        }
    }
}
