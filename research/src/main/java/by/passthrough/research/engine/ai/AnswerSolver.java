package by.passthrough.research.engine.ai;

import by.passthrough.research.engine.server.HostThread;
import by.passthrough.research.entities.messages.RequestMessage;
import by.passthrough.research.entities.messages.ResponseMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    public ResponseMessage handleRequest(HostThread hostThread, RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        String cmd = requestMessage.getCommand();
        switch (cmd){
            case "peers": {
                JSONArray peers = new JSONArray();
                for(HostThread aHostThread: hostThread.getConnectionsManager().getThreads()){
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
