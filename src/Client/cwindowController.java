package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Filip on 23.05.2017.
 */
public class cwindowController implements Initializable {

    public void setClient(ChatEventListener client) {
        this.client = client;
    }

    ChatEventListener client;
    ExecutorService executorService;
    boolean cond;

    @FXML
    private TextArea textArea;


    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cond = true;
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                String buf = null;
                while (cond) {
                    try {
                        buf = client.getInput().readUTF();
                        textArea.appendText(buf+"\n");
                        System.out.println(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executorService = Executors.newFixedThreadPool(3);
        executorService.submit(reader);
    }

    public void sendButtonAction(ActionEvent actionEvent) throws IOException {
        client.sendMsg(textField.getText());
        //textArea.appendText(textField.getText()+"\n");
        textField.clear();
    }
}
