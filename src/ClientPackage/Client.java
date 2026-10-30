package ClientPackage;

import main.java.commonPackage.Operation;
import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connecté au serveur " + socket.getRemoteSocketAddress());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Lire le numéro attribué par le serveur
            int clientId = ois.readInt();
            System.out.println(">>> Je suis le client n°" + clientId + " <<<");

            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US);
            String encore;

            do {
                System.out.print("Premier opérande : ");
                double x = sc.nextDouble();

                String op;
                do {
                    System.out.print("Opérateur (+ - * /) : ");
                    op = sc.next();
                } while (!op.matches("[+\\-*/]"));

                System.out.print("Deuxième opérande : ");
                double y = sc.nextDouble();

                // Envoyer l'objet Operation
                Operation operation = new Operation(x, op.charAt(0), y);
                oos.writeObject(operation);
                oos.flush();

                // Lire la réponse
                String reponse = (String) ois.readObject();
                System.out.println("Résultat : " + reponse);

                System.out.print("Autre calcul ? (o/n) : ");
                encore = sc.next();
            } while (encore.equalsIgnoreCase("o"));

            oos.writeObject("FIN"); // Fin de communication
            System.out.println("Fin de la communication.");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}