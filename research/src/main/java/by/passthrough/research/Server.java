package by.passthrough.research;

import by.passthrough.research.engine.server.ConnectionsManager;
import by.passthrough.research.entities.ServerAnswers;
import by.passthrough.research.utils.Logger;

import java.util.Random;

public class Server {
    private static Logger log = Logger.createLogger(Server.class, true);

    public static void main(String[] args){

        try(ConnectionsManager cm = new ConnectionsManager()){
            log.info("Server started");
            cm.listen(CustomHostThread.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomAnswer() {
        int randIndex = (new Random()).nextInt(ServerAnswers.values().length);
        ServerAnswers value = ServerAnswers.values()[randIndex];
        return "I feel " + value;
    }

    private static String getAnswerToClient(String messageFromClient) {
        String serverAnswer;
        log.debug("Answer begin");
        switch (messageFromClient) {
            case "hello":
                serverAnswer = "Hi there!";
                break;
            case "what's up?":
                serverAnswer = getRandomAnswer();
                break;
            case "are you sure?":
                serverAnswer ="Give me alone!";
                break;
            default:
                serverAnswer = "Please, read carefully instruction above, stupid leather bastard";
        }
        log.debug("Answer finish= " + serverAnswer);
        return serverAnswer;
    }

    private static String drawWelcomeWords() {
        log.info("Trying to build welcome message to server...");
        return new StringBuilder().append("============================\n")
                .append("Welcome to Chat, username!\n")
                .append("============================\n")
                .append("Please use next commands for communication with server\n")
                .append("1 - hello\n")
                .append("2 - what's up?\n")
                .append("3 - are you sure?\n")
                .append("4 - see you\n")
                .append("============================")
                .toString();
    }
}
