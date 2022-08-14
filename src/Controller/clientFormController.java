package Controller;

import com.sun.corba.se.pept.transport.ListenerThread;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.util.Timer;


public class clientFormController extends Thread{
    public TextArea txtArea;
    public TextField txtMessage;
    public ImageView txtSendImg;
    public AnchorPane root;

    public BufferedReader reader;
    public PrintWriter writer;
    public Socket socket;
    public Label lblName;


    public void initialize(){

        lblName.setText(severFormController.Username);
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Socket is connected with server successfully...!");
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            while (true) {

                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }
                System.out.println(fullMsg);

                System.out.println("cmd="+cmd+"-----"+"UserName"+lblName.getText());
                if(!cmd.equalsIgnoreCase(lblName.getText()+":")){
                    txtArea.appendText(msg + "\n");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public void messageOnAction(ActionEvent actionEvent) throws IOException {
            String msg = txtMessage.getText();
            writer.println(lblName.getText() + ": " + txtMessage.getText());
            txtMessage.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            txtArea.appendText("Me: " + msg + "\n");
            txtMessage.clear();
            if(msg.equalsIgnoreCase("Bye")||(msg.equalsIgnoreCase("Logout"))) {
                System.exit(0);
            }
        }
        public void clear(ActionEvent actionEvent) {
            txtArea.clear();
        }
}
