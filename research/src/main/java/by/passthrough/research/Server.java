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

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(5450)) {
            System.out.println("Waiting for connection from client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection approved");
            DataInputStream dataFromClient = new DataInputStream(clientSocket.getInputStream());
            System.out.println("Input stream created");
            DataOutputStream dataFromCurrentServer = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Output stream created");
            drawWelcomeWords();
            while (!clientSocket.isClosed()) {
                String messageFromClient = dataFromClient.readUTF().toLowerCase();
                if ("see you".equalsIgnoreCase(messageFromClient)) {
                    System.out.println("Client initialize connections suicide ...");
                    dataFromCurrentServer.writeUTF("Bitch!");
                    dataFromCurrentServer.flush();
                    break;
                }
                String serverAnswer = getAnswerToClient(messageFromClient);
                dataFromCurrentServer.writeUTF(serverAnswer);
                System.out.println("Server Wrote message to client.");
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
        String serverAnswer = "I feel myself" + value;
        return serverAnswer;
    }

    private static String getAnswerToClient(String messageFromClient) {
        String serverAnswer;
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
        return serverAnswer;
    }

    private static void drawWelcomeWords() {
        System.out.println("============================");
        System.out.println("Welcome to Chat, username!");
        System.out.println("============================");
        System.out.println("Please use next commands for communication with server");
        System.out.println("hello");
        System.out.println("what's up?");
        System.out.println("are you sure?");
        System.out.println("see you");
        System.out.println("============================");
    }
}
