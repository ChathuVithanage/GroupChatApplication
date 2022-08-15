package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import Thread.Flusher;
import java.util.HashMap;
import java.util.Timer;

public class severFormController {

    public TextField txtUsername;
    public static String Username;
    public AnchorPane root;

    Stage stage;

    Server server;
    Thread mainThread;

    Socket localSocket;
    public static HashMap<Integer,DataOutputStream> clients = new HashMap<>();

    public void initialize() throws IOException {

        // open up the server
        server = new Server(8080, 5);

        mainThread = new Thread(() -> {
            while (true) {
                try {
                    localSocket = server.accept();
                    Timer timer = new Timer();
                    timer.schedule(new Flusher(new DataInputStream(localSocket.getInputStream()),timer),0,2000);
                    clients.put(localSocket.getPort(), new DataOutputStream(localSocket.getOutputStream()));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        mainThread.start();

        Platform.runLater(() -> {
            stage.setOnCloseRequest(e -> {
                mainThread.interrupt();
                // TODO : have to check why jvm is not exiting from above code block
                System.exit(0);
            });
        });
    }

    public void LogOnAction(ActionEvent actionEvent) throws IOException {

        // opening chatArea
        Stage clientStage = new Stage(StageStyle.DECORATED);
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("View/clientForm.fxml"));
        Scene client = new Scene(loader.load());

        clientStage.setScene(client);
        clientStage.setTitle("User : " + txtUsername.getText());
        clientStage.setResizable(false);
        clientStage.sizeToScene();

        try {
            clientFormController controller = loader.getController();
            controller.initData(txtUsername.getText(),clientStage);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        clientStage.show();
        txtUsername.clear();

    }

    public void initData (Stage stage) {
        this.stage = stage;
    }

}

