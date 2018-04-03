package by.passthrough.research.engine.transceivers;

import by.passthrough.research.utils.Logger;
import by.passthrough.research.utils.configurator.Configurator;
import by.passthrough.research.utils.configurator.LoadConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PeerTransceiver extends AbstractTransceiver {
    private static Logger log = Logger.createLogger(PeerTransceiver.class);

    @LoadConfig
    private String host = "localhost";

    @LoadConfig
    private int port = 62333;

    public PeerTransceiver() {
        Configurator.getInstance().configure(this);
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
