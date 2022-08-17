package Controller;

import com.sun.security.ntlm.NTLMException;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import Thread.ListenerThread;
import util.Client;
import static org.apache.commons.lang.ArrayUtils.addAll;


public class clientFormController{
    public Pane txtArea;
    public TextField txtMessage;
    public ImageView txtSendImg;
    public AnchorPane root;

    public BufferedReader reader;
    public PrintWriter writer;

    public Socket socket;
    public Label lblName;
    public VBox msgBox;
    public ScrollPane scrollPane;
    public Pane emojiPane;

    Client client;
    String txtUsername;
    ListenerThread listener;
    Stage stage;

    int localPort;
    DataOutputStream localOutputStream;

    FileChooser fileChooser;

    byte[] payload;
    byte[] header;
    byte[] sender;
    byte[] frame;

    int mouseCounter = 0;

    public void initialize() throws IOException, NTLMException {

        Platform.runLater(() -> {
            // add a listener for scrollbar to be at the end
            msgBox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
            stage.setOnCloseRequest(e -> {
                listener.stop();
            });
        });

        Connect();

    }

    private void Connect() throws NTLMException, IOException {
        client = new Client(txtUsername,"localhost",8080);

        Timer timer = new Timer();
        fileChooser = new FileChooser();

        // TODO : open up a listener for server
        try {
            listener = new ListenerThread(new DataInputStream(client.getInputStream()),msgBox,timer);
            timer.schedule(listener,0,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMsg(String txtUsername) throws IOException {

        if (!txtMessage.getText().equals("")){
            String msg = txtUsername + " : " + txtMessage.getText();
            payload = msg.getBytes(StandardCharsets.UTF_16);
            int len = payload.length;

            header = ByteBuffer.allocate(4).putInt(len).array();
            byte[] frame = addAll(header,payload);

            client.getOut().write(0);
            client.getOut().write(frame);
            client.getOut().flush();

            return true;
        } else return false;
    }


    public void initData(String name, Stage stage) {
        this.stage = stage;
        this.txtUsername = name;
    }


    // TODO : pass the file type before sending file
    public void uploadPhoto(MouseEvent mouseEvent) throws IOException, InterruptedException {

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile!=null) {

            String[] res = selectedFile.getName().split("\\.");

            BufferedImage finalImage = ImageIO.read(selectedFile);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ImageIO.write(finalImage, res[1], bout);


            payload = bout.toByteArray();
            header = ByteBuffer.allocate(4).putInt(payload.length).array();

            byte[] frame = ArrayUtils.addAll(header,payload);

            String msg = txtUsername + " : ";
            byte[] payload2 = msg.getBytes(StandardCharsets.UTF_16);
            int len2 = payload.length;

            byte[] header2 = ByteBuffer.allocate(4).putInt(len2).array();
            byte[] frame2 = addAll(header2,payload2);

            client.getOut().write(0);
            client.getOut().write(frame2);
            client.getOut().flush();

            Thread.sleep(2000);

            client.getOut().write(-1);
            client.getOut().write(frame);

            client.getOut().flush();

        }

    }

    public void OpenEmoji(MouseEvent event) {
        txtMessage.setText(txtMessage.getText() + "\uD83D\uDE18");
        txtMessage.setText(txtMessage.getText() + "\uD83D\uDE0D");
        txtMessage.setText(txtMessage.getText() + "\uD83D\uDE02");
        txtMessage.setText(txtMessage.getText() + "\uD83D\uDE12");
        txtMessage.setText(txtMessage.getText() + "\uD83D\uDE48");
        txtMessage.setText(txtMessage.getText() + "\uD83D\uDE21");

    }

    public void messageOnAction(ActionEvent event) throws IOException {
        if(sendMsg(txtUsername)){
            txtMessage.clear();
        }
    }

    public void sendOnEnter(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(sendMsg(txtUsername)){
                txtMessage.clear();
            }
        }
    }

}
