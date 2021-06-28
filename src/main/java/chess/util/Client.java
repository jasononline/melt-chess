package chess.util;

import chess.model.Piece;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Class representing a Client for the Network Game mode
 */
public class Client {

    private static Socket socket;
    private static DataOutputStream dataOutputStream;

    /**
     * Initializes and connects the Client
     * @param ipAddress the ip Address to connect to
     * @param port the port to connect to
     * @throws IOException
     */
    public static void initialize(String ipAddress, int port) throws IOException {
        //endOldClient();
        socket = new Socket(ipAddress, port);
        System.out.println("Client socket setup.");
        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream
        dataOutputStream = new DataOutputStream(outputStream);
    }

    /**
     * This will communicate with the opponent-Server to decide who will begin the game
     * @return Piece.White or Piece.Black, the color one will play
     * @throws IOException
     * @throws InterruptedException
     */
    public static int decideColor() throws InterruptedException, IOException {
        System.out.println("decideColor() method call.");
        Random rand = new Random();
        int result = rand.nextInt(100) + 1;
        String resultString = String.valueOf(result);
        String opponentString = "";
        int opponentNumber;

        System.out.println("Begin to decide Color... " + "My Number is: " + resultString);

        send(resultString);
        while (opponentString == "") {
            System.out.println("Opponent string is Empty.");
            try {
                opponentString = Server.read();
            } catch (IOException e) {
                System.out.println("Reading did not work, retrying...");
            }
            System.out.println("new Opponent String is: " + opponentString);
            send(String.valueOf(resultString));
            Thread.sleep(1000);
        }
        send(resultString);

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

    /**
     * This will send a String message to a previously connected Server
     * @param message the message to send to the connected server
     * @throws IOException
     */
    public static void send(String message) throws IOException {
        if (dataOutputStream == null) {
            return;
        }
        dataOutputStream.writeUTF(message);
        System.out.println("This message has been sent: " + message);
    }

    public static void endOldClient() throws IOException {
        System.out.println("If there already was a Client, it will now be closed.");
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}