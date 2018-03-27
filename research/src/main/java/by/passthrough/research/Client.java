package by.passthrough.research;

import by.passthrough.research.utils.Logger;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Logger log = Logger.createLogger(Client.class, true);
    private static int PORT = 5450;

    public static void main(String[] args) throws InterruptedException, IOException {
        try (Socket connectedSocket = new Socket("localhost",PORT);
            BufferedReader clientData = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dataToServer = new DataOutputStream(connectedSocket.getOutputStream());
            DataInputStream dataFromServer = new DataInputStream(connectedSocket.getInputStream()))
        {
            log.info("Client connected to socket.");
            log.debug("Client writing channel = dataToServer & reading channel = dataFromServer initialized.");
            while (!connectedSocket.isOutputShutdown()) {
                if (clientData.ready()) {
                    log.info("Client start writing in channel...");
                    String clientMessage = clientData.readLine();
                    dataToServer.writeUTF(clientMessage);
                    dataToServer.flush();
                    String messageFromClient = dataFromServer.readUTF();

                    log.info(messageFromClient);
                    if (!dataFromServer.readBoolean()){
                        log.info("Client disconnected");
                        break;
                    }
                }

            }
            log.info("Server killed connection");
            if (dataFromServer.read() > -1) {
                log.info("Reading ...");
                String serverMessage = dataFromServer.readUTF();
                log.info(serverMessage);
            }
        }
    }
}
