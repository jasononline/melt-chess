package chess.util;

import chess.gui.game.GameModel;
import chess.model.Game;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

/**
 * Class representing a Server for the Network Game mode
 */
public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static InputStream inputStream;
    private static DataInputStream dataInputStream;

    /**
     * initializes the Server
     * @throws IOException IOException
     */
    public static void initialize() throws IOException {
        serverSocket = new ServerSocket(1905);
        String ip = InetAddress.getLocalHost().getHostAddress();
        //System.out.println("A Server has been setup. ip = " + ip + ", port = " + serverSocket.getLocalPort());
        //System.out.println("ServerSocket awaiting connections...");
        System.out.println("\n******************************************************");
        System.out.println("Other Players are now able to connect to you!");
        System.out.println("If you want someone to connect share IP-Addr and Port:");
        System.out.println("IP-Address:\t" + "\033[43m" + ip + "\033[0;0m");
        System.out.println("Port:\t\t" + "\033[43m" + serverSocket.getLocalPort() + "\033[0;0m");
        System.out.println("******************************************************\n");
        socket = serverSocket.accept(); // blocking call, this will wait until a connection is attempted on this port.
        System.out.println("\n******************************************************");
        System.out.println("An other Player connected to you!");
        System.out.println("If you want to connect to that Player choose:");
        System.out.println("IP-Address:\t" + "\033[42m" + socket.getInetAddress().getHostAddress() + "\033[0;0m");
        System.out.println("Port:\t\t" + "\033[42m" + socket.getLocalPort() + "\033[0;0m");
        System.out.println("******************************************************\n");
        // get the input stream from the connected socket
        inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        dataInputStream = new DataInputStream(inputStream);
    }

    /**
     * Reads the next message from the server socket
     * @return the next message from the server socket
     * @throws IOException IOException
     */
    public static String read() throws IOException {
        if (dataInputStream == null) {
            // System.out.println("Not initialized yet.");
            return "" ;
        }
        String message = dataInputStream.readUTF();
        System.out.println("The message sent from the socket was: " + message);
        return message;
    }

    /**
     * Reads the next message from the server socket, ignores unexpected messages.
     * Should only be called from background thread since this method polls the server socket until finding legit input
     * @return the next legal Opponent message
     * @throws IOException IOException
     */
    public static String getOpponentInput() throws IOException {
        System.out.println("I am listening to Opponent input!");
        String input = read();
        while (!testUserInputSyntax(input)) {
            if(GameModel.isStopTask()) {
                return "";
            }
            input = read();
            if (input != "" && !testUserInputSyntax(input)) {
                System.out.println("Probably illegal Opponent input was: " + input + " (will be ignored)");
            }
        }

        System.out.println("Probably legal Opponent input was: " + input);

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
     * @throws IOException
     */
    public static void endOldServer() throws IOException {
        System.out.println("If there already was a Server, it will now be closed.");
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}