package by.passthrough.research.engine.ai;

import by.passthrough.research.engine.server.ConnectionsManager;
import by.passthrough.research.engine.server.HostThread;
import by.passthrough.research.entities.messages.RequestMessage;
import by.passthrough.research.entities.messages.ResponseMessage;
import org.json.simple.JSONArray;

public class AnswerSolver {

    private static AnswerSolver INSTANCE = null;

    private AnswerSolver(){

    }

    public static AnswerSolver getInstance() {
        if(INSTANCE == null){
            INSTANCE = new AnswerSolver();
        }
        return INSTANCE;
    }

    public String reverse(String str){
        StringBuffer sb = new StringBuffer();
        for( int i=str.length() - 1; i>=0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
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
