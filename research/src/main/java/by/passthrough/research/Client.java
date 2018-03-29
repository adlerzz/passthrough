package by.passthrough.research;

import by.passthrough.research.engine.transceivers.PeerTransceiver;
import by.passthrough.research.utils.Logger;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Client {
    private static Logger log = Logger.createLogger(Client.class, true);
    private static int PORT = 5450;
    private static String STOP_WORD = "stop";

//    public static void main(String[] args) throws IOException {
//        try (Socket connectedSocket = new Socket("localhost",PORT);
//            BufferedReader clientData = new BufferedReader(new InputStreamReader(System.in));
//            DataOutputStream dataToServer = new DataOutputStream(connectedSocket.getOutputStream());
//            DataInputStream dataFromServer = new DataInputStream(connectedSocket.getInputStream()))
//        {
//            log.info("Client connected to socket.");
//            log.debug("Client writing channel = dataToServer & reading channel = dataFromServer initialized.");
//            while (!connectedSocket.isOutputShutdown()) {
//                if (clientData.ready()) {
//                    log.info("Client start writing in channel...");
//                    String clientMessage = clientData.readLine();
//                    dataToServer.writeUTF(clientMessage);
//                    dataToServer.flush();
//                    String messageFromClient = dataFromServer.readUTF();
//
//                    log.info(messageFromClient);
//                    if (!dataFromServer.readBoolean()){
//                        log.info("Client disconnected");
//                        break;
//                    }
//                }
//
//            }
//            log.info("Server killed connection");
//            if (dataFromServer.read() > -1) {
//                log.info("Reading ...");
//                String serverMessage = dataFromServer.readUTF();
//                log.info(serverMessage);
//            }
//        }
//    }

    public static void main(String[] args){

        try(PeerTransceiver peer = new PeerTransceiver("10.229.90.186", PORT)) {
            BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));

            String id = String.valueOf( (new Date()).getTime() );

            JSONObject firstMessage = new JSONObject();
            firstMessage.put("stopWord", STOP_WORD);
            firstMessage.put("id", id);

            peer.open();
            peer.send(firstMessage.toString());

            Thread inputThread = new Thread(){
                @Override
                public void run(){
                    while (!this.isInterrupted()){
                        try {
                            String msg = reader.readLine();
                            peer.send(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            inputThread.start();

            String recv;
            boolean end = false;
            do{
                recv = peer.receive();
                if(STOP_WORD.equals(recv)){
                    end = true;
                } else {
                    log.debug("received: \"" + recv + "\"");
                }
            } while (!end);
            inputThread.interrupt();

        } catch (IOException e) {
            log.error(e);
        }
    }

}
