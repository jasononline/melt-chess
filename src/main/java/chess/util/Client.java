package chess.util;

import chess.model.Piece;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
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
     * @throws IOException IOException
     */
    public static boolean initialize(String ipAddress, int port) {
        try {
            InetAddress.getByName(ipAddress).isReachable(500);
            socket = new Socket(ipAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // get the output stream from the socket.
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // create a data output stream from the output stream
        dataOutputStream = new DataOutputStream(outputStream);
        return true;
    }

    /**
     * This will communicate with the opponent-Server to decide who will begin the game
     * @return Piece.White or Piece.Black, the color one will play
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    public static int decideColor() throws InterruptedException, IOException {
        System.out.println("decideColor() method call.");
        Random rand = new Random();
        int result = rand.nextInt(100) + 1;
        String resultString = String.valueOf(result);
        String opponentString = "";
        int opponentNumber;

        // System.out.println("Begin to decide Color... " + "My Number is: " + resultString);

        send(resultString);
        while (opponentString == "") {
            // System.out.println("Opponent string is Empty.");
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
            return Piece.White;
        } else if (result > opponentNumber) {
            return Piece.Black;
        }

        return 0;
    }

    /**
     * This will send a String message to a previously connected Server
     * @param message the message to send to the connected server
     * @throws IOException IOException
     */
    public static void send(String message) throws IOException {
        if (dataOutputStream == null) {
            return;
        }
        if (message.equals("resign")) {
            Server.lastOpponentInput = "resign";
        }
        dataOutputStream.writeUTF(message);
        System.out.println("This message has been sent: " + message);
    }

    /**
     * This mehtod will close an existing Client socket and output stream
     * @throws IOException
     */
    public static void endOldClient() throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}