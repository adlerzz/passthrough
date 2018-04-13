package by.passthrough.research.engine.server.ai;

import by.passthrough.research.engine.server.AbstractHostThread;
import by.passthrough.research.entities.messages.RequestMessage;
import by.passthrough.research.entities.messages.ResponseMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Singleton for various methods for handling messages
 */
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

    /**
     * Could be called to getting response message to request message
     * @param hostThread which thread call handling
     * @param requestMessage actually request message
     * @return appropriated response message
     */
    public ResponseMessage handleRequest(AbstractHostThread hostThread, RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        String cmd = requestMessage.getCommand();
        switch (cmd){
            case "peers": {

                ArrayList<AbstractHostThread> allThreads = hostThread.getConnectionsManager().getThreads();
                JSONArray peers = new JSONArray();
                for(AbstractHostThread aHostThread: allThreads){
                    if(!hostThread.equals(aHostThread) ) {
                        JSONObject peer = new JSONObject();
                        peer.put("user", aHostThread.get("user"));
                        peers.add(peer);
                    }
                }
                responseMessage.setId(requestMessage.getId());
                responseMessage.setPayload(peers);
                break;
            }
        }
        return responseMessage;
    }
}
