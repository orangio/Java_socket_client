package Client;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Filip on 23.05.2017.
 */
public interface ChatEventListener {
    int login(String a, String b) throws IOException;
    void logout() throws IOException;
    void sendMsg(String a) throws IOException;
    DataInputStream getInput();
}
