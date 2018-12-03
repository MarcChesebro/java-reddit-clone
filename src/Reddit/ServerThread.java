package Reddit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    private static int threadCount;
    private int user;
    private Socket connection;
//    private PageList pageList;
    private ArrayList<Page> pages;

    public ServerThread(Socket socket) throws Exception {
        this.connection = socket;
        this.pages = new ArrayList<Page>();
        Page p = new Page("NewPage");
        p.addPost("hello world 1112341234!", "");
        p.getPosts().get(0).addComment("test", "test111");
        this.pages.add(p);
        saveCurrentPageList();
    }

//    public void addPage(String Title){
//        this.pageList.addPage(Title);
//    }
//
//    public void addPost(String pageTitle, String postText, String image){
//        //TODO this is assuming the image has been downloaded already.
//        Page page = this.pageList.getPage(pageTitle);
//        if (page != null){
//            page.addPost(postText, image);
//        }
//    }

    public void saveCurrentPageList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data.ser", true);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(fileOut));
            for (int i = 0; i < pages.size(); i++) {
                out.writeObject(pages.get(i));
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("error in writing users");
        }
    }

    public void saveNewPageList(ArrayList<Page> newPages) {
        try {
            FileOutputStream fileOut = new FileOutputStream("data.ser", true);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(fileOut));
            for (int i = 0; i < newPages.size(); i++) {
                out.writeObject(newPages.get(i));
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("error in writing users");
        }
    }

    public void loadPageList() {
        pages = new ArrayList<Page>();
        int count = 0;
        FileInputStream saveFile;
        try {
            saveFile = new FileInputStream("data.ser");
            try {
                ObjectInputStream save = new ObjectInputStream(new BufferedInputStream(saveFile));
                for (; ; ) {
                    pages.add((Page) save.readObject());
                    count++;
                }
            } catch (Exception e) {

            } finally {
                saveFile.close();
            }
        } catch (EOFException e) {
            System.out.println("exception in write");
        } catch (Exception exc) {
            //System.out.println("exception in write");
        }
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
        user = threadCount++;
        System.out.println("Client" + user + " has connected!");

        // read input from user
        while (true) {

            String command = inFromClient.readLine(); //TODO get from client
            if (command.startsWith("update")) {
                //TODO get pages from client and save them into the data.ser file also update self.pages

            } else if (command.startsWith("retr")) {
                //TODO send pages to clientDataInputStream dataIn = new DataInputStream(fileIn);
                ObjectOutputStream objectOut = new ObjectOutputStream(outToClient);
                for(int i = 0; i < this.pages.size(); i++) {
                    objectOut.writeObject(this.pages.get(i));
                }

            } else if (command.startsWith("images")){
                //TODO send images to client
            }
        }
    }
}
