package by.passthrough.research.engine.ai;

import by.passthrough.research.engine.server.ConnectionsManager;
import by.passthrough.research.engine.server.HostThread;
import by.passthrough.research.entities.messages.RequestMessage;
import by.passthrough.research.entities.messages.ResponseMessage;
import org.json.simple.JSONArray;

public final class AnswerSolver {

    private static volatile AnswerSolver instance = null;

    private AnswerSolver(){

    }

    public static AnswerSolver getInstance() {
        if(instance == null){
            synchronized (AnswerSolver.class) {
                if(instance == null) {
                    instance = new AnswerSolver();
                }
            }
        }
        return instance;
    }

    public ResponseMessage handleRequest(ConnectionsManager connectionsManager, RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        String cmd = requestMessage.getCommand();
        switch (cmd){
            case "peers": {
                JSONArray peers = new JSONArray();
                for(HostThread hostThread: connectionsManager.getThreads()){
                    peers.add( hostThread.get("id"));
                }
                responseMessage.setId(requestMessage.getId());
                responseMessage.setPayload(peers);

                break;
            }

        }
        return responseMessage;
    }
}
