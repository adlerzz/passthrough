package by.passthrough.research;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws InterruptedException, IOException {
        try (Socket connectedSocket = new Socket("localhost",5450);
            BufferedReader clientData = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dataToServer = new DataOutputStream(connectedSocket.getOutputStream());
            DataInputStream dataFromServer = new DataInputStream(connectedSocket.getInputStream()))
        {
            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = dataToServer & reading channel = dataFromServer initialized.");
            while (!connectedSocket.isOutputShutdown()) {
                Thread.sleep(5000);
                if (clientData.ready()) {
                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);
                    String clientMessage = clientData.readLine();
                    dataToServer.writeUTF(clientMessage);
                    Thread.sleep(1000);
                    dataToServer.flush();
                    String messageFromClient = dataFromServer.readUTF();

                    System.out.println(messageFromClient);
                    if (!dataFromServer.readBoolean()){
                        System.out.println("Client disconnected");
                        break;
                    }

                }
                Thread.sleep(2000);

            }
            System.out.println("Server killed connection");
            if (dataFromServer.read() > -1) {
                System.out.println("Reading ...");
                String serverMessage = dataFromServer.readUTF();
                System.out.println(serverMessage);
            }
        }
    }
}
