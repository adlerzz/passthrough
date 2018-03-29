package by.passthrough.research.engine.server;

import by.passthrough.research.engine.transceivers.HostTransceiver;
import by.passthrough.research.utils.Logger;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by alst0816 on 28.03.2018
 */
public class ConnectionsManager implements Closeable {
    private static Logger log = Logger.createLogger(ConnectionsManager.class, true);
    private ExecutorService pool;
    private HashMap<String, HostThread> hostThreads;
    private ServerSocket serverSocket;

    public ConnectionsManager(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
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
