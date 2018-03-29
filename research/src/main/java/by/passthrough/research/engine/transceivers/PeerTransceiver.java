package by.passthrough.research.engine.transceivers;

import by.passthrough.research.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PeerTransceiver extends AbstractTransceiver {
    private static Logger log = Logger.createLogger(PeerTransceiver.class, true);
    private String host;
    private int port;

    public PeerTransceiver(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void open() throws IOException {
        this.clientSocket = new Socket(this.host, this.port);
        this.inputStream = new DataInputStream(this.clientSocket.getInputStream());
        this.outputStream = new DataOutputStream(this.clientSocket.getOutputStream());
    }

    @Override
    public void close() throws IOException {
        this.clientSocket.close();
    }

    @Override
    public void send(String message) throws IOException {
        super.send(message);
        log.debug("sended \"" + message + "\"");
    }

    @Override
    public String receive() throws IOException {
        String res = super.receive();
        log.debug("received \"" + res + "\"");
        return res;
    }
}
