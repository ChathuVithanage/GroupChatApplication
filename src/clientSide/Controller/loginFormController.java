package clientSide.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public class loginFormController {

    public TextField txtUsername;
    public static String Username;
    public static ArrayList<String> users = new ArrayList<>();
    public AnchorPane root;

    public void LogOnAction(ActionEvent actionEvent) throws IOException {
        Username = txtUsername.getText().trim();

        boolean flag = false;
        if (users.isEmpty()) {
            users.add(Username);
            flag = true;
        }

        for (String s : users) {
            if (!s.equalsIgnoreCase(Username)) {
                flag = true;
                System.out.println(Username);
                break;
            }
        }
        if (flag) {
            this.root.getChildren().clear();
            this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("../View/clientForm.fxml")));
        }

    }
}
