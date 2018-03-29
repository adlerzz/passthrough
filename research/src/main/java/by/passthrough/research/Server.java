package by.passthrough.research;

import by.passthrough.research.engine.server.ConnectionsManager;
import by.passthrough.research.entities.ServerAnswers;
import by.passthrough.research.utils.Logger;

import java.util.Random;

public class Server {
    private static int PORT = 5450;
    private static Logger log = Logger.createLogger(Server.class, true);

//    public static void main(String[] args) throws IOException{
//        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            log.info("Waiting for connection from client...");
//            Socket clientSocket = serverSocket.accept();
//            log.info("Connection approved");
//            DataInputStream dataFromClient = new DataInputStream(clientSocket.getInputStream());
//            log.info("Input stream created");
//            DataOutputStream dataFromCurrentServer = new DataOutputStream(clientSocket.getOutputStream());
//            log.info("Output stream created");
//
//            boolean connectionFlag = true;
//            while (!clientSocket.isClosed()) {
//
//                String messageFromClient = dataFromClient.readUTF().toLowerCase();
//                log.info("messageFromClient= " + messageFromClient);
//                if ("see you".equalsIgnoreCase(messageFromClient)) {
//                    log.info("Client initialize connections suicide ...");
//                    connectionFlag = false;
//                    dataFromCurrentServer.writeUTF("Connection kiled by server!");
//                    dataFromCurrentServer.writeBoolean(connectionFlag);
//                    dataFromCurrentServer.flush();
//                    break;
//                }
//                String serverAnswer = getAnswerToClient(messageFromClient);
//                log.info("Server Wrote message= " + serverAnswer +" to client.");
//                dataFromCurrentServer.writeUTF(serverAnswer);
//                dataFromCurrentServer.writeBoolean(connectionFlag);
//                dataFromCurrentServer.flush();
//
//            }
//            log.info("Client disconnected");
//            log.info("Closing connections & channels.");
//            dataFromClient.close();
//            dataFromCurrentServer.close();
//
//        }
//    }

    public static void main(String[] args){

        try(ConnectionsManager cm = new ConnectionsManager(PORT)){

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
