package Controller;

import com.sun.corba.se.pept.transport.Acceptor;
import com.sun.corba.se.pept.transport.ListenerThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;

public class severFormController {

    public TextField txtUsername;
    public static String Username;
    public AnchorPane root;

    /*public void initialize() throws IOException {
        // setting up the server
        System.out.println("server up and running!");
        int PORT = 8000;
        serverSocket  = new ServerSocket(PORT);
        // wait for an connection to be established --> binds it to a local socket --> returns the remote socket
        Thread serverWaitingThread = new Thread(() -> {
            try {
                localSocket = serverSocket.accept();
                System.out.println("connection succeeded!");

                outputStream = new DataOutputStream(localSocket.getOutputStream());
                inputStream = new DataInputStream(localSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverWaitingThread.start();
    }*/

    public void LogOnAction(ActionEvent actionEvent) throws IOException {
        Username=txtUsername.getText();

        Stage stage1 = (Stage) txtUsername.getScene().getWindow();
        stage1.close();
        Stage stage2=new Stage();
        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/clientForm.fxml"))));
        stage2.setResizable(false);
        stage2.setTitle("sample title");
        stage2.centerOnScreen();
        stage2.show();
    }
}
