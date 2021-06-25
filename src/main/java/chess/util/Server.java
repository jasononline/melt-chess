package chess.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static InputStream inputStream;
    private static DataInputStream dataInputStream;

    public static void initialize() throws IOException {
        serverSocket = new ServerSocket(1905);
        String ip = InetAddress.getLocalHost().getHostAddress().toString();
        System.out.println("A Server has been setup. ip = " + ip + "\nport = " + serverSocket.getLocalPort());
        System.out.println("ServerSocket awaiting connections...");
        socket = serverSocket.accept(); // blocking call, this will wait until a connection is attempted on this port.
        System.out.println("Connection from " + socket + "!");
        // get the input stream from the connected socket
        inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        dataInputStream = new DataInputStream(inputStream);
    }

    public static String read() throws IOException {
        if (dataInputStream == null) {
            // System.out.println("Not initialized yet.");
            return "" ;
        }
        String message = dataInputStream.readUTF();
        System.out.println("The message sent from the socket was: " + message);
        return message;
    }

    public static void quit() throws IOException {
        if (!(serverSocket == null)) {
            serverSocket.close();
        }
        if (!(socket == null)) {
            socket.close();
        }
    }

    public static String getOpponentInput() throws IOException {
        System.out.println("I am listening to Opponent input!");
        String input = read();
        while (!testUserInputSyntax(input)) {
            input = read();
            if (input != "" && !testUserInputSyntax(input)) {
                System.out.println("Probably illegal Opponent input was: " + input + " (will be ignored)");
            }
        }

        System.out.println("Probably legal Opponent input was: " + input);

        return input;
    }

    private static boolean testUserInputSyntax(String userInput) {
        // Checks if input matches one of valid inputs: move(e7-e8[Q]), resign
        return userInput.matches("^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[qrbn]?$|^resign$");
    }
}