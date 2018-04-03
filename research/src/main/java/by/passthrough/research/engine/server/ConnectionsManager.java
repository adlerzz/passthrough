package by.passthrough.research.engine.server;

import by.passthrough.research.utils.Logger;
import by.passthrough.research.utils.configurator.Configurator;
import by.passthrough.research.utils.configurator.LoadConfig;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionsManager implements Closeable {
    private static Logger log = Logger.createLogger(ConnectionsManager.class, true);
    private ExecutorService pool;
    private HashMap<String, HostThread> hostThreads;
    private ServerSocket serverSocket;

    @LoadConfig(name = "PORT", defaultValue = "62333")
    private String port;

    public ConnectionsManager() throws IOException {
        Configurator.getInstance().configure(this);
        this.serverSocket = new ServerSocket(Integer.parseInt(this.port));
        this.pool = Executors.newFixedThreadPool(10);
        this.hostThreads = new HashMap<>();
    }


    public <T extends HostThread> void listen(Class<T> hostThreadClass) throws Exception {
        while(!this.serverSocket.isClosed()){
            Socket clientSocket = this.serverSocket.accept();
            log.debug("new thread");
            HostThread hostThread = hostThreadClass.newInstance();
            hostThread.setConnectionsManager(this);
            hostThread.setClientSocket(clientSocket);
            this.pool.submit(hostThread);
        }
    }

    synchronized public void addNamedThread(String id, HostThread hostThread){
        this.hostThreads.put(id, hostThread);
        log.debug("added thread " + id);
    }

    public HostThread getThreadById(String id){
        return this.hostThreads.get(id);
    }

    public ArrayList<HostThread> getThreads(){
        return new ArrayList<>(this.hostThreads.values());
    }

    @Override
    public void close() throws IOException {
        this.pool.shutdown();
        this.serverSocket.close();
    }
}
