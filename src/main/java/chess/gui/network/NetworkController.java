package chess.gui.network;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * Controls the behaviour and actions of the UI elements in the network scene.
 * In this scene the user can enter the IP, port and start to establish a connection.
 */


        public class NetworkController {

            @FXML
            private TextField IPAddresstxtf;

            @FXML
            private TextField Porttxtf;



            @FXML
            void handleButtonCancelOnAction(ActionEvent event) {
                String IPAddress = IPAddresstxtf.getText();
                String PortAddress = Porttxtf.getText();
                System.out.println("User clicked on connection in the Main Menu");
                System.out.println("The IP Address: "+IPAddress);
                System.out.println("The Port Address: "+PortAddress);

                // TODO Establish connection
            }

            @FXML
            private void handleConnectSaveOnAction() {
                System.out.println("User clicked on cancel in the Main Menu");
            }

        }


