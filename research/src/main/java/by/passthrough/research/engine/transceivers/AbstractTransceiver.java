package by.passthrough.research.engine.transceivers;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class AbstractTransceiver implements Closeable {
    Socket clientSocket = null;
    DataInputStream  inputStream = null;
    DataOutputStream outputStream = null;

    public abstract void open() throws IOException;

    public void send(String message) throws IOException {
        outputStream.writeUTF(message);
        outputStream.flush();
    }

    public String receive() throws IOException {
        while(inputStream == null || inputStream.available() < 1){
            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return inputStream.readUTF();
    }
}
