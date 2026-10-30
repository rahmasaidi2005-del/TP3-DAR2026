package act31;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int compteur = 0;

    public static synchronized int getNumeroClient() {
        compteur++;
        return compteur;
    }

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(1235);

            System.out.println("Serveur démarré...");

            while (true) {

                Socket socket = serverSocket.accept();

                int numero = getNumeroClient();

                System.out.println(
                        "Client n°" + numero +
                                " connecté : " +
                                socket.getRemoteSocketAddress()
                );

                ClientProcess cp =
                        new ClientProcess(socket, numero);

                cp.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}