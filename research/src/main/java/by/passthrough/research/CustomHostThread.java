package by.passthrough.research;

import by.passthrough.research.engine.ai.AnswerSolver;
import by.passthrough.research.engine.server.HostThread;
import by.passthrough.research.utils.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CustomHostThread extends HostThread {
    private static Logger log = Logger.createLogger(CustomHostThread.class, true);
    public CustomHostThread(){
        super();
    }

    @Override
    public void prepare() throws Exception {
        String recv = this.receive();
        try {

            JSONObject firstMessage = (JSONObject) jsonParser.parse(recv);
            if(firstMessage.containsKey("id") && firstMessage.containsKey("stopWord") ){
                this.put("stopWord", firstMessage.get("stopWord"));
                this.put("id", firstMessage.get("id"));
                this.getConnectionsManager().addNamedThread((String)firstMessage.get("id"), this);
            } else{
                throw new java.text.ParseException("no required keys", -1);
            }


            this.send("stopWord OK");
        } catch (Exception e) {
            this.send("stopWord ERROR");
        }
    }

    @Override
    public void doAction() throws Exception {
        String recv = this.receive();
        if(this.get("stopWord").equals(recv)){
            this.stop = true;
            this.send(recv);
        } else {

            try{
                JSONObject message = (JSONObject) jsonParser.parse(recv);
                if("CHAT".equals(message.get("type")) && message.containsKey("dest")){
                    HostThread dest = this.getConnectionsManager().getThreadById((String)message.get("dest"));
                    dest.send((String)message.get("content"));
                }

                if("DATA".equals(message.get("type")) && "peers".equals(message.get("cmd"))){
                    JSONArray peers = new JSONArray();
                    for(HostThread hostThread: this.getConnectionsManager().getThreads()){
                        peers.add( hostThread.get("id"));
                    }
                    this.send(peers.toJSONString());
                }

            } catch (ParseException e) {
                this.send(AnswerSolver.getInstance().reverse(recv));
            }

        }
    }
}
