package Reddit;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception{

        ServerSocket welcomeSocket = new ServerSocket(12000);

        while (true) {
            //Wait for request at welcome socket
            Socket connectionSocket = welcomeSocket.accept();

            //create a new thread for the user
            ServerThread userConnection = new ServerThread(connectionSocket);
            Thread user_thread = new Thread(userConnection);

            // start the thread
            user_thread.start();
        }

    }
}
