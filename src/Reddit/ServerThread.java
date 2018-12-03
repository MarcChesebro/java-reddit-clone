package Reddit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable{

    private static int threadCount;
    private int user;
    private Socket connection;

    public ServerThread(Socket socket) throws Exception {
        this.connection = socket;
    }

    // this runs on start after the thread has been setup
    public void run() {
        try {
            processCommand();
        } catch (Exception e) {}
        System.out.println("Client has disconnected!");
    }

    private void processCommand() throws Exception {
        DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        user = threadCount;
        System.out.println("Client" + threadCount++ + " has connected!");
        // read input from user
        while (true) {
            //commands run
        }
    }
}
