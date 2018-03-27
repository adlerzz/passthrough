package by.passthrough.research;

import by.passthrough.research.entities.ServerAnswers;
import by.passthrough.research.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Server {

    private static int PORT = 5450;

    private static final List<ServerAnswers> VALUES =
            Collections.unmodifiableList(Arrays.asList(ServerAnswers.values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    private static Logger log = Logger.createLogger(Server.class, true);

    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Waiting for connection from client...");
            Socket clientSocket = serverSocket.accept();
            log.info("Connection approved");
            DataInputStream dataFromClient = new DataInputStream(clientSocket.getInputStream());
            log.info("Input stream created");
            DataOutputStream dataFromCurrentServer = new DataOutputStream(clientSocket.getOutputStream());
            log.info("Output stream created");

            boolean connectionFlag = true;
            while (!clientSocket.isClosed()) {

                String messageFromClient = dataFromClient.readUTF().toLowerCase();
                log.info("messageFromClient= " + messageFromClient);
                if ("see you".equalsIgnoreCase(messageFromClient)) {
                    log.info("Client initialize connections suicide ...");
                    connectionFlag = false;
                    dataFromCurrentServer.writeUTF("Connection kiled by server!");
                    dataFromCurrentServer.writeBoolean(connectionFlag);
                    dataFromCurrentServer.flush();
                    break;
                }
                String serverAnswer = getAnswerToClient(messageFromClient);
                log.info("Server Wrote message= " + serverAnswer +" to client.");
                dataFromCurrentServer.writeUTF(serverAnswer);
                dataFromCurrentServer.writeBoolean(connectionFlag);
                dataFromCurrentServer.flush();

            }
            log.info("Client disconnected");
            log.info("Closing connections & channels.");
            dataFromClient.close();
            dataFromCurrentServer.close();

        }
    }

    private static String getRandomAnswer() {
        ServerAnswers value = VALUES.get(RANDOM.nextInt(SIZE));
        String serverAnswer = "I feel " + value;
        return serverAnswer;
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
