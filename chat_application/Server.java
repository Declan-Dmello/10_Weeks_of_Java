package chat_application;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println(" Chat Server started on port " + PORT);
        System.out.println("The chat Logs \n" +
                "");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask client for a name
                out.println("Enter your name:");
                clientName = in.readLine();

                System.out.println(clientName + " connected.");
                out.println("Welcome, " + clientName + "! Start chatting.");

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    String formattedMessage = clientName + ": " + message;
                    System.out.println("Message -> " + formattedMessage);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(formattedMessage);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(clientName + " disconnected.");
            } finally {
                try { socket.close(); } catch (IOException e) { }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }
}
