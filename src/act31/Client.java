package act31;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try {

            Socket socket =
                    new Socket("localhost", 1235);

            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            String message = in.readLine();

            System.out.println(message);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}