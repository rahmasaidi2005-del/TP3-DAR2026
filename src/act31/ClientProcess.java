package act31;



import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess extends Thread {

    private Socket socket;
    private int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {

        try {

            PrintWriter out =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true
                    );

            out.println(
                    "Bienvenue Client n°" + numero
            );

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}