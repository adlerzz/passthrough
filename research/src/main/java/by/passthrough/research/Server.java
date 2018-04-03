package by.passthrough.research;

import by.passthrough.research.engine.server.ConnectionsManager;
import by.passthrough.research.utils.Logger;

public class Server {
    private static Logger log = Logger.createLogger(Server.class, true);

    public static void main(String[] args){

        try(ConnectionsManager cm = new ConnectionsManager()){
            log.info("Server started in port " + cm.getPort());
            cm.listen(CustomHostThread.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
