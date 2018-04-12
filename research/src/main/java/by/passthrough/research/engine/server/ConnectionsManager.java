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
    private static Logger log = Logger.createLogger(ConnectionsManager.class);
    private ExecutorService pool;
    private HashMap<Long, HostThread> hostThreads;
    private ServerSocket serverSocket;

    @LoadConfig
    private int port = 62333;

    @LoadConfig(name = "MAX_CONNECTIONS")
    private int maxThreads = 120;

    public ConnectionsManager() throws IOException {
        Configurator.getInstance().configure(this);
        this.serverSocket = new ServerSocket(this.port);
        this.pool = Executors.newFixedThreadPool(maxThreads);
        this.hostThreads = new HashMap<>();
    }

    public int getPort(){
        return this.port;
    }

    public <T extends HostThread> void listen(Class<T> hostThreadClass) throws Exception {
        while(!this.serverSocket.isClosed()){
            Socket clientSocket = this.serverSocket.accept();
            HostThread hostThread = hostThreadClass.newInstance();
            hostThread.setConnectionsManager(this);
            hostThread.setClientSocket(clientSocket);
            this.pool.submit(hostThread);
            log.debug("new thread");
        }
    }

    synchronized public void addNamedThread(long id, HostThread hostThread){
        this.hostThreads.put(id, hostThread);
        log.debug("added thread " + id);
    }

    synchronized public void removeNamedThread(long id){
        this.hostThreads.remove(id);
        log.debug("removed thread " + id);
    }

    public HostThread getThreadById(long id){
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
