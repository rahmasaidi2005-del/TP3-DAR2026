package ServerPackage;

import main.java.commonPackage.Operation;
import java.io.*;
import java.net.Socket;

public class ClientProcess implements Runnable {

    private final Socket socket;
    private final int clientId;

    private static int compteurGlobal = 0;
    private static final Object lock = new Object();

    public ClientProcess(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeInt(clientId);
            oos.flush();

            Object recu;

            while (true) {
                recu = ois.readObject();

                if (recu instanceof String && recu.equals("FIN")) {
                    break;
                }

                if (recu instanceof Operation) {
                    Operation op = (Operation) recu;

                    String reponse = calculer(
                            op.getOperande1(),
                            op.getOperateur(),
                            op.getOperande2()
                    );

                    oos.writeObject(reponse);
                    oos.flush();

                    synchronized (lock) {
                        compteurGlobal++;

                        System.out.println("[Client n°" + clientId + "] Opération : "
                                + op.getOperande1() + " "
                                + op.getOperateur() + " "
                                + op.getOperande2() + " => " + reponse);

                        System.out.println("Nombre total d'opérations traitées : "
                                + compteurGlobal);
                    }
                }
            }

            System.out.println("Client n°" + clientId + " s'est déconnecté.");

        } catch (Exception e) {
            System.out.println("Déconnexion du client n°" + clientId);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du socket.");
            }
        }
    }

    private String calculer(double a, char operateur, double b) {
        double r;

        switch (operateur) {
            case '+':
                r = a + b;
                break;

            case '-':
                r = a - b;
                break;

            case '*':
                r = a * b;
                break;

            case '/':
                if (b == 0)
                    return "Erreur : division par zéro impossible";
                r = a / b;
                break;

            default:
                return "Erreur : opérateur inconnu";
        }

        return a + " " + operateur + " " + b + " = " + r;
    }
}

