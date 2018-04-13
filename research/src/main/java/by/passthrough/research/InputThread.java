package by.passthrough.research;

import by.passthrough.research.engine.transceivers.PeerTransceiver;
import by.passthrough.research.entities.messages.*;
import by.passthrough.research.entities.users.User;
import by.passthrough.research.utils.CommonUtils;
import by.passthrough.research.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputThread extends Thread {

    private static Logger log = Logger.createLogger(InputThread.class);
    private BufferedReader reader;
    private PeerTransceiver peerTransceiver;
    private User user;


    public InputThread(PeerTransceiver peerTransceiver, User user) {
        this.reader = new BufferedReader( new InputStreamReader(System.in));
        this.peerTransceiver = peerTransceiver;
        this.user = user;

    }

    @Override
    public void run(){
        String sendMsg;
        while (!this.isInterrupted()){

            String[] msg = new String[0];
            try {
                msg = this.reader.readLine().split(" ", 2);
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
            sendMsg = null;
            switch (firstWord) {

                case "name":
                    user.setNickname(secondWord);
                    log.debug("set nickname as \"" + user.getNickname() + "\"");
                    break;

                case "auth":
                    AuthMessage authMessage = new AuthMessage();
                    authMessage.setId(user.getId());
                    authMessage.setUser(user);

                    try {
                        this.peerTransceiver.open();
                    } catch (IOException e){
                        log.error(e);
                    }
                    sendMsg = authMessage.toString();
                    break;

                case "allPeers":
                    RequestMessage requestMessage = new RequestMessage(CommonUtils.getInstance().getNewId(),"peers");
                    sendMsg = requestMessage.toString();
                    break;

                case "test":
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSrc( user.getId() );
                    chatMessage.setDest( Long.valueOf(secondWord) );
                    chatMessage.setPayload("test message from " + user.getNickname());
                    sendMsg = chatMessage.toString();
                    break;

                case "end":
                    sendMsg = SystemMessage.STOP.toString();
                    break;

                default:
                    sendMsg = firstWord + " " + secondWord;
            }

            if(sendMsg != null) {
                try {
                    this.peerTransceiver.send(sendMsg);
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }
}
