package chess.util;

import chess.gui.game.GameModel;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class representing a Server for the Network Game mode
 */
public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static InputStream inputStream;
    private static DataInputStream dataInputStream;
    protected static String lastOpponentInput;

    /**
     * initializes the Server
     * @throws IOException IOException
     */
    public static void initialize() throws IOException {
        serverSocket = new ServerSocket(0);
        socket = serverSocket.accept(); // blocking call, this will wait until a connection is attempted on this port.
        inputStream = socket.getInputStream(); // get the input stream from the connected socket
        dataInputStream = new DataInputStream(inputStream); // create a DataInputStream so we can read data from it.
    }

    /**
     * Reads the next message from the server socket
     * @return the next message from the server socket
     * @throws IOException IOException
     */
    public static String read() throws IOException {
        if (dataInputStream == null) {
            // System.out.println("#Debug: Not initialized yet.");
            return "" ;
        }
        String message = dataInputStream.readUTF();
        //System.out.println("#Debug: " + dataInputStream.skip(10));
        // System.out.println("#Debug: The message sent from the socket was: " + message);
        return message;
    }

    /**
     * Reads the next message from the server socket, ignores unexpected messages.
     * Should only be called from background thread since this method polls the server socket until finding legit input
     * @return the next legal Opponent message
     * @throws IOException IOException
     */
    public static String getOpponentInput() throws IOException {
        // System.out.println("#Debug: I am listening to Opponent input!");
        String input = read();
        while (!testUserInputSyntax(input.toLowerCase())) {
            if(GameModel.isTaskStopped()) {
                // System.out.println("#Debug: getOpponentInput() was caused to stop.");
                return "";
            }
            input = read();
            if (input.equals(lastOpponentInput)) {
                // ignore input that has been sent directly before
                continue;
            }
        }

        // System.out.println("#Debug: Probably legal Opponent input was: " + input);
        lastOpponentInput = input;

        return input;
    }

    /**
     * Tests whether a String is a legal Opponent action
     * @param userInput
     * @return true if userInput is legal, else false
     */
    private static boolean testUserInputSyntax(String userInput) {
        // Checks if input matches one of valid inputs: move(e7-e8[Q]), resign
        return userInput.matches("^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[qrbn]?$|^resign$");
    }

    /**
     * Closes an active Server
     * @throws IOException IOExeption
     */
    public static void endOldServer() throws IOException {
        // System.out.println("#Debug: If there already was a Server, it will now be closed.");
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (socket != null) {
            socket.close();
        }
        lastOpponentInput = "";
    }

    /**
     * Getter for the port of the Server
     * @return the port of the Server
     */
    public static int getPort() {
        if (serverSocket != null) {
            return serverSocket.getLocalPort();
        }
        return 0;
    }

    /**
     * Getter for the ip of the Server
     * @return the ip of the Server
     */
    public static String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}