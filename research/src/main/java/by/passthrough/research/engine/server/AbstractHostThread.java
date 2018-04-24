package by.passthrough.research.engine.server;

import by.passthrough.research.engine.transceivers.HostTransceiver;
import by.passthrough.research.utils.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * It is the class that should be a superclass for the concrete server thread.
 * This class realizes some general functionality related to linkage with
 * connection manager, sockets, incapsulates sending and receiving data.
 * In inheriting class just should be implemented how the server
 * thread should react to messages from clients.
 * @see by.passthrough.research.CustomHostThread CustomHostThread
 */
public abstract class AbstractHostThread implements Callable<Void>, Closeable {
    protected Logger log = Logger.createLogger(AbstractHostThread.class);
    private HostTransceiver host;
    protected volatile boolean stop;
    protected long id;
    private Socket clientSocket;
    protected Map<String, Object> data;
    private ConnectionsManager connectionsManager;

    public AbstractHostThread(){
        this.data = new HashMap<>();
        this.stop = false;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setConnectionsManager(ConnectionsManager connectionsManager) {
        this.connectionsManager = connectionsManager;
    }

    public ConnectionsManager getConnectionsManager() {
        return connectionsManager;
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public Object get(String key){
        return this.data.get(key);
    }

    private String receive() throws IOException {
        return this.host.receive();
    }

    public void send(String msg) throws IOException{
        this.host.send(msg);
    }

    @Override
    public Void call() throws Exception {
        this.host = new HostTransceiver(clientSocket);
        this.host.open();

        this.prepare(this.receive());
        do {
            this.doAction(this.receive());
        } while (!this.stop);

        log.debug("thread " + this.id + " stopped");
        this.getConnectionsManager().removeNamedThread(this.id);
        return null;
    }

    public abstract void prepare(String received);

    public abstract void doAction(String received) throws Exception;

    @Override
    public void close() throws IOException {
        this.clientSocket.close();
        this.host.close();
    }
}
