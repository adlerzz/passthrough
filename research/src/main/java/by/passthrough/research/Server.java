package by.passthrough.research;

import by.passthrough.research.engine.server.ConnectionsManager;
import by.passthrough.research.utils.Logger;

/**
 * Main class of server part
 */
public class Server {
    private static Logger log = Logger.createLogger(Server.class);

    /**
     * Creates new connection manager and enforce it listen to connections
     *  @see ConnectionsManager
     */
    public static void main(String[] args){

        try(ConnectionsManager cm = new ConnectionsManager()){
            log.info("Server started in port " + cm.getPort());
            cm.listen(CustomHostThread.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
