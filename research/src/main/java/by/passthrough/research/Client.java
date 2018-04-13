package by.passthrough.research;

import by.passthrough.research.engine.transceivers.PeerTransceiver;
import by.passthrough.research.entities.messages.*;
import by.passthrough.research.entities.users.User;
import by.passthrough.research.utils.CommonUtils;
import by.passthrough.research.utils.Logger;

import java.io.IOException;

/**
 * Main class of client part
 */
public class Client {
    private static Logger log = Logger.createLogger(Client.class);

    public static void main(String[] args){
        User user = new User();
        user.setId( CommonUtils.getInstance().getNewId() );
        try(PeerTransceiver peer = new PeerTransceiver()) {

            InputThread inputThread = new InputThread(peer, user);
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
