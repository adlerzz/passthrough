package by.passthrough.research;

import by.passthrough.research.engine.transceivers.PeerTransceiver;
import by.passthrough.research.entities.messages.AuthMessage;
import by.passthrough.research.entities.messages.ChatMessage;
import by.passthrough.research.entities.messages.Message;
import by.passthrough.research.entities.messages.MessageType;
import by.passthrough.research.entities.messages.RequestMessage;
import by.passthrough.research.entities.messages.SystemMessage;
import by.passthrough.research.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Client {
    private static Logger log = Logger.createLogger(Client.class, true);

    public static void main(String[] args){

        try(PeerTransceiver peer = new PeerTransceiver()) {
            BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));

            String id = String.valueOf( (new Date()).getTime() );

            AuthMessage authMessage = new AuthMessage();
            authMessage.setName(id);

            peer.open();
            peer.send(authMessage.toJSONString());

            Thread inputThread = new Thread(){
                @Override
                public void run(){
                    while (!this.isInterrupted()){
                        try {
                            String[] msg = reader.readLine().split(" ", 2);
                            String firstWord = msg[0];
                            String secondWord = null;
                            if(msg.length > 1){
                                secondWord = msg[1];
                            }
                            switch (firstWord) {
                                case "allPeers":
                                    RequestMessage requestMessage = new RequestMessage(String.valueOf((new Date().getTime())), "peers");
                                    peer.send(requestMessage.toJSONString());
                                break;

                                case "test":
                                    ChatMessage chatMessage = new ChatMessage();
                                    chatMessage.setDest(secondWord);
                                    chatMessage.setPayload("test message");
                                    peer.send(chatMessage.toJSONString());
                                break;

                                default:
                                    peer.send(secondWord + " " + firstWord);
                            }
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
                Message msg = Message.parseFromJSON(recv);

                MessageType messageType = msg.getMessageType();
                switch (messageType){
                    case SYSTEM:
                        SystemMessage systemMessage = (SystemMessage)msg;
                        if(SystemMessage.STOP.getPayload().equals(systemMessage.getPayload())){
                            end = true;
                        }
                    break;

                    case CHAT:
                        ChatMessage chatMessage = (ChatMessage)msg;
                        System.out.println(chatMessage.getPayload());
                    break;
                }
            } while (!end);
            inputThread.interrupt();

        } catch (IOException e) {
            log.error(e);
        }
    }

}
