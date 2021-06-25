package chess.util;

import chess.model.Piece;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class Client {

    private static Socket socket;
    private static OutputStream outputStream;
    private static DataOutputStream dataOutputStream;

    public static void initialize(String ipAddress, int port) throws IOException {
        socket = new Socket(ipAddress, port);
        System.out.println("Client socket setup.");
        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public static int decideColor() throws IOException, InterruptedException {
        Random rand = new Random();
        int result = rand.nextInt(100) + 1;
        String opponentString = "";
        int opponentNumber;

        System.out.println("Begin to decide Color... " + "My Number is: " + result);
        send(String.valueOf(result));

        while (opponentString == "") {
            System.out.println("Opponent string is Empty.");
            opponentString = Server.read();
            send(String.valueOf(result));
            Thread.sleep(3000);
        }
        System.out.println("OpponentNumber is: " + opponentString);
        opponentNumber = Integer.parseInt(opponentString);

        if (result < opponentNumber) {
            System.out.println("Return White");
            return Piece.White;
        } else if (result > opponentNumber) {
            System.out.println("Return Black");
            return Piece.Black;
        }

        return 0;
    }

    public static void send(String message) throws IOException {
        if (dataOutputStream == null) {
            return;
        }
        dataOutputStream.writeUTF(message);
        System.out.println("This message has been sent: " + message);
    }

    public static void quit() throws IOException {
        if (!(dataOutputStream == null)) {
            dataOutputStream.close();
        }
        if (!(socket == null)) {
            socket.close();
        }

    }
}