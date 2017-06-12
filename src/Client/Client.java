package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Filip on 20.05.2017.
 */
public class Client  extends Application implements Closeable, ChatEventListener{
    private  boolean cond=true;
    private boolean logged=false;
    private String msg;
    private DataOutputStream Output;
    private DataInputStream Input;
    private Socket skt;

    public static void main(String[] args) {
        launch(args);
        Client c = new Client();
        return;
    }
    public Client() {
        final boolean cond=true;
        String host= "localhost";
        int port = 999;
        Initializable controller;

        Scanner odczyt = new Scanner(System.in);
        try{
            //Próba połączenia z serwerem
            System.out.println("Klient: Próba podłączenia do serwera jako host-"+host+" port: "+port+'.');
             skt = new Socket(host,port);

            Output = new DataOutputStream(new BufferedOutputStream(skt.getOutputStream()));
            Input = new DataInputStream(new BufferedInputStream(skt.getInputStream()));
            //Opcje odczytu i zapisu z i do strumienia
            /*
            Thread reader = new Thread(new Runnable() {
                @Override
                public void run() {
                    String buf = null;
                    while(!logged)
                    {

                    }
                    while (cond) {
                        try {
                            buf = Input.readUTF();
                            System.out.println("Odpowiedź serwera [ " + buf + " ]");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            Thread writer = new Thread(new Runnable() {
                @Override
                public void run() {
                    String buf = null;
                    while (cond) {
                        try {
                            buf = odczyt.nextLine();
                            Output.writeUTF(buf);
                            Output.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            ExecutorService executorService = Executors.newFixedThreadPool(2);
            executorService.submit(reader);
            //executorService.submit(writer);
            */
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Uuuups, coś się skopało. nie podziałam!");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("lwindow.fxml"));
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        Parent root=loader.getRoot();

        lwindowController lcontroller = loader.getController();
        lcontroller.setClient(this);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static String[] arguments;


    @Override
    public void close() throws IOException {
        Output.writeUTF("3");
        Output.flush();
        cond=false;
    }
    public void register() throws IOException {
        Output.writeUTF("1");
        Output.flush();
    }
    public void logged() throws IOException {
        Output.writeUTF("2");
        Output.flush();
    }

    @Override
    public int login(String login, String pass) throws IOException {
        System.out.println("weszłem");
        Output.writeUTF(login);
        Output.flush();
        Output.writeUTF(pass);
        Output.flush();
        int result = Input.readInt();
        System.out.println(result);
        logged=true;
        return result;
    }

    @Override
    public void logout() throws IOException {
        Output.writeUTF("3");
        Output.flush();
    }

    @Override
    public void sendMsg(String msg) throws IOException {
        Output.writeUTF("4"+msg);
        Output.flush();
    }

    @Override
    public DataInputStream getInput() {
        return Input;
    }


}
