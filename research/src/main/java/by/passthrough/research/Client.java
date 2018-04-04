package by.passthrough.research;

import by.passthrough.research.engine.transceivers.PeerTransceiver;
import by.passthrough.research.entities.messages.*;
import by.passthrough.research.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Client {
    private static Logger log = Logger.createLogger(Client.class);

    public static void main(String[] args){

        try(PeerTransceiver peer = new PeerTransceiver()) {
            BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));

            String id = String.valueOf( (new Date()).getTime() );

            AuthMessage authMessage = new AuthMessage();
            authMessage.setName(id);

            peer.open();
            peer.send(authMessage.toString());

            Thread inputThread = new Thread(){
                @Override
                public void run(){
                    String sendMsg;
                    while (!this.isInterrupted()){

                        String[] msg = new String[0];
                        try {
                            msg = reader.readLine().split(" ", 2);
                            if(this.isInterrupted()){
                                break;
                            }
                        } catch (IOException e) {
                            log.error(e);
                        }
                        String firstWord = msg[0];
                        String secondWord = null;
                        if(msg.length > 1){
                            secondWord = msg[1];
                        }
                        switch (firstWord) {
                            case "allPeers":
                                RequestMessage requestMessage = new RequestMessage(String.valueOf((new Date().getTime())), "peers");
                                sendMsg = requestMessage.toString();
                            break;

                            case "test":
                                ChatMessage chatMessage = new ChatMessage();
                                chatMessage.setDest(secondWord);
                                chatMessage.setPayload("test message");
                                sendMsg = chatMessage.toString();
                            break;

                            case "end":
                                sendMsg = SystemMessage.STOP.toString();
                            break;

                            default:
                                sendMsg = firstWord + " " + secondWord;
                        }

                        try {
                            peer.send(sendMsg);
                        } catch (IOException e) {
                            log.error(e);
                        }
                    }
                }
            };

            inputThread.start();

            String recv;
            boolean end = false;
            do{
                recv = peer.receive();
                Message msg = Message.parseFromJSON(recv);

                MessageType messageType = msg.getMessageType();
                switch (messageType){
                    case SYSTEM: {
                        if (SystemMessage.STOP.equals(msg)) {
                            end = true;
                        }
                    } break;

                    case CHAT: {
                        ChatMessage chatMessage = (ChatMessage) msg;
                        System.out.println(chatMessage.getPayload());
                    } break;
                }
            } while (!end);
            inputThread.interrupt();

        } catch (IOException e) {
            log.error(e);
        }
    }

}
