package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Filip on 23.05.2017.
 */
public class lwindowController implements Initializable {

    public void setClient(ChatEventListener client) {
        this.client = client;
    }

    ChatEventListener client;
    cwindowController cController;

    @FXML
    private Button button;

    @FXML
    private TextField userField;
    @FXML
    private Label label;

    @FXML
    private TextField passField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void exitButtonAction(ActionEvent actionEvent) throws IOException {
        client.logout();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void loginButtonAction(ActionEvent actionEvent) {
        Parent root;
        try {
            int result = client.login(userField.getText(),passField.getText());
            if(result==1) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cwindow.fxml"));
                try
                {
                    fxmlLoader.load();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return;
                }
                Parent root1=fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("ABC");
                stage.setScene(new Scene(root1));
                cController = fxmlLoader.getController();
                cController.setClient(client);
                stage.show();
                /*
                root = FXMLLoader.load(getClass().getClassLoader().getResource("Client.cwindow.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Chat");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                */
            } else {
                userField.clear();
                passField.clear();
                if (result == 2)
                    label.setText("User already logged, retry");
                else
                    label.setText(result + "Login failed, retry");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
