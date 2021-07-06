package chess.util.networkthreads;

import chess.util.Server;

import java.io.IOException;

/**
 * This is used as a Thread to initialize the Server since in Server.initialize() there is a blocking statement.
 */
public class InitServerThread implements Runnable {
    @Override
    public void run() {
        // System.out.println("#Debug: InitServerThread started.");
        try {
            Server.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("#Debug: InitServerThread finished.");
    }
}
