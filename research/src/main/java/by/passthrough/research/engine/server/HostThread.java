package by.passthrough.research.engine.server;

import by.passthrough.research.engine.transceivers.HostTransceiver;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class HostThread implements Callable<Void>, Closeable {
    private HostTransceiver host;
    protected volatile boolean stop;
    private Socket clientSocket;
    private Map<String, Object> data;
    private ConnectionsManager connectionsManager;

    public HostThread(){
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

    protected String receive() throws IOException {
        return this.host.receive();
    }

    public void send(String msg) throws IOException{
        this.host.send(msg);
    }

    @Override
    public Void call() throws Exception {
        this.host = new HostTransceiver(clientSocket);
        this.host.open();
        this.prepare();
        do {
            this.doAction();
        } while (!this.stop);
        return null;
    }

    public abstract void prepare() throws Exception;

    public abstract void doAction() throws Exception;

    @Override
    public void close() throws IOException {
        this.clientSocket.close();
        this.host.close();
    }
}
