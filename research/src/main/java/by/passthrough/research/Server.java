package by.passthrough.research;

import by.passthrough.research.entities.ServerAnswers;

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
    private static final List<ServerAnswers> VALUES =
            Collections.unmodifiableList(Arrays.asList(ServerAnswers.values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket serverSocket = new ServerSocket(5450)) {
            System.out.println("Waiting for connection from client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection approved");
            DataInputStream dataFromClient = new DataInputStream(clientSocket.getInputStream());
            System.out.println("Input stream created");
            DataOutputStream dataFromCurrentServer = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Output stream created");

            while (!clientSocket.isClosed()) {
                Thread.sleep(5000);
                String messageFromClient = dataFromClient.readUTF().toLowerCase();
                System.out.println("messageFromClient= " + messageFromClient);
                if ("see you".equalsIgnoreCase(messageFromClient)) {

                    System.out.println("Client initialize connections suicide ...");
                    dataFromCurrentServer.writeUTF("Connection kiled by server!");
                    dataFromCurrentServer.writeBoolean(false);
                    dataFromCurrentServer.flush();
                    break;
                }
                String serverAnswer = getAnswerToClient(messageFromClient);
                System.out.println("Server Wrote message= " + serverAnswer +" to client.");
                dataFromCurrentServer.writeUTF(serverAnswer);
                dataFromCurrentServer.flush();

            }
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
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
        System.out.println("Answer begin");
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
        System.out.println("Answer finish= " + serverAnswer);
        return serverAnswer;
    }

    private static void drawWelcomeWords(DataOutputStream dataFromCurrentServer) {
        System.out.println("Trying to biuild welcome message to server...");
        StringBuilder builder = new StringBuilder();
        String welcomeMessage = builder.append("============================\n")
                .append("Welcome to Chat, username!\n")
                .append("============================\n")
                .append("Please use next commands for communication with server\n")
                .append("1 - hello\n")
                .append("2 - what's up?\n")
                .append("3 - are you sure?\n")
                .append("4 - see you\n")
                .append("============================")
                .toString();
        try {
            dataFromCurrentServer.writeUTF(welcomeMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
