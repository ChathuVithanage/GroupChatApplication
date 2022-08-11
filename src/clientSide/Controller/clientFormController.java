package clientSide.Controller;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class clientFormController extends Thread{
    public TextArea txtArea;
    public TextField txtMessage;
    public ImageView txtSendImg;
    public AnchorPane root;

    BufferedReader reader;
    PrintWriter writer;

    public clientFormController(){

        txtMessage.setEditable(false);
        txtArea.setEditable(false);//name ek unique kyl proof wenkam

    }

    public void messageOnAction(ActionEvent actionEvent) {
        String msg = txtMessage.getText().trim();
        writer.println(loginFormController.Username + ": " + msg);
        txtArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtMessage.setText("");
        if (msg.equalsIgnoreCase("Bye") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    /*DataOutputStream outputStream;
    DataInputStream inputStream;
    ServerSocket serverSocket;

    public void initialize() {
        System.out.println(loginFormController.Username);
        try {
            socket = new Socket("localhost", 9001);
            System.out.println("Connected.....");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                StringBuilder fullMessage = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMessage.append(tokens[i]);
                }

                System.out.println(fullMessage);

                if (cmd.equalsIgnoreCase(loginFormController.Username + ": ")) {
                    continue;
                } else if (fullMessage.toString().equalsIgnoreCase("bye")) {
                    break;
                }

                txtMessage.appendText(msg + "\n");
            }

            reader.close();
            writer.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(ActionEvent actionEvent) throws IOException {
        String msg = txtMessage.getText();
        txtArea.appendText("\nMe : " + msg);
        outputStream.writeUTF(msg);
        outputStream.flush();
        txtMessage.clear();
    }



    public DataInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }*/

}
