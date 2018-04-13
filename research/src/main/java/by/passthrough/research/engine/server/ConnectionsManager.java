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

/**
 * ConnectionsManager is a class which holds and manages
 * the pool of server connection threads.
 * @see AbstractHostThread
 * @see #listen(Class)
 * @see #addNamedThread(long, AbstractHostThread)
 * @see #getThreads()
 * @see #getThreadById(long)
 * @see #removeNamedThread(long)
 */
public class ConnectionsManager implements Closeable {
    private static Logger log = Logger.createLogger(ConnectionsManager.class);
    private ExecutorService pool;
    private HashMap<Long, AbstractHostThread> hostThreads;
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

    /**
     * Method should be called for waiting the new client connections and creating new threads
     * @param hostThreadClass what kind of thread should be created due to new connection
     */
    public <T extends AbstractHostThread> void listen(Class<T> hostThreadClass) throws Exception {
        while(!this.serverSocket.isClosed()){
            Socket clientSocket = this.serverSocket.accept();
            AbstractHostThread hostThread = hostThreadClass.newInstance();
            hostThread.setConnectionsManager(this);
            hostThread.setClientSocket(clientSocket);
            this.pool.submit(hostThread);
            log.debug("new thread");
        }
    }

    /**
     * Method could be called for storing thread instances to thread map
     * with binding them the ID for ability access to them by this ID
     * @param id unique ID
     * @param hostThread stored thread
     */
    synchronized public void addNamedThread(long id, AbstractHostThread hostThread){
        this.hostThreads.put(id, hostThread);
        log.debug("added thread " + id);
    }

    /**
     * Method could be called for excluding thread instances from thread map
     * @param id unique ID
     */
    synchronized public void removeNamedThread(long id){
        this.hostThreads.remove(id);
        log.debug("removed thread " + id);
    }

    /**
     * Method could be called for getting thread instance from thread map by ID
     * @param id unique ID
     * @return thread instance
     */
    public AbstractHostThread getThreadById(long id){
        return this.hostThreads.get(id);
    }

    /**
     * Method could be called for getting all thread instances
     * @return all thread instances
     */
    public ArrayList<AbstractHostThread> getThreads(){
        return new ArrayList<>(this.hostThreads.values());
    }

    @Override
    public void close() throws IOException {
        this.pool.shutdown();
        this.serverSocket.close();
    }
}
