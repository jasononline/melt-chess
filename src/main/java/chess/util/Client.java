package chess.util;

import chess.gui.game.GameModel;
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
    private static String ipAddress;
    private static int port;
    private static int oldOpponentNumber = -2;

    /**
     * Initializes and connects the Client
     * @return true if ipAddress is reacheable
     * @throws IOException IOException
     */
    public static boolean initialize() throws IOException {
        socket = new Socket(ipAddress, port);

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
        // System.out.println("decideColor() method call.");
        Random rand = new Random();
        int result;

        do {
            result = rand.nextInt(100) + 1;
        } while (result == oldOpponentNumber);

        String resultString = String.valueOf(result);
        String opponentString = "";
        int opponentNumber = 0;

        send(resultString);
        while (opponentString == "") {
            if (GameModel.isTaskStopped()) {
                return 0;
            }
            try {
                String serverInput = Server.read();
                if (!serverInput.equals(String.valueOf(oldOpponentNumber))) {
                    opponentString = serverInput;
                    opponentNumber = Integer.parseInt(opponentString);
                }
            } catch (IOException e) {
                // System.out.println("#Debug: Reading did not work, retrying...");
                e.printStackTrace();
            } catch (NumberFormatException e) {
                // System.out.println("#Debug: There was a NumberFormatException in Client.decideColor().");
                opponentString = "";
            }
            // System.out.println("#Debug: new Opponent String is: " + opponentString);
            send(resultString);
            Thread.sleep(1000);
        }
        send(resultString);

        // System.out.println("#Debug: OpponentNumber is: " + opponentString);

        if (result < opponentNumber) {
            return Piece.White;
        } else if (result > opponentNumber) {
            return Piece.Black;
        } else {
            // System.out.println("#Debug: Similar Numbers were generated");
            oldOpponentNumber = opponentNumber;
            return decideColor();
        }
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
        // System.out.println("#Debug: This message has been sent: " + message);
    }

    /**
     * This method will close an existing Client socket and output stream
     * @throws IOException IOException
     */
    public static void endOldClient() throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    /**
     * Method to set where to connect to
     * @param ip the ip Address
     * @param port the port
     */
    public static void setConnectionValues(String ip, int port) {
        ipAddress = ip;
        Client.port = port;
    }

    /**
     * Getter for the IPAddress
     * @return the IPAddress
     */
    public static String getIpAddress() {
        return ipAddress;
    }
}