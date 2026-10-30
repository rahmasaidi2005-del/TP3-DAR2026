package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final AtomicInteger clientCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur multi-thread (Calculatrice) démarré sur le port 1234");
            System.out.println("En attente de clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                int clientId = clientCounter.incrementAndGet();

                System.out.println("Client n°" + clientId + " connecté depuis " + socket.getRemoteSocketAddress());

                // Utilisation du nouveau Handler pour la calculatrice
                ClientProcess handler = new ClientProcess(socket, clientId);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}