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
    private ArrayList<Image> images;

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
            FileOutputStream fileOut = new FileOutputStream("data.ser");
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

    public void loadPageList() {
        ArrayList<Page> newPages = new ArrayList<Page>();
        int count = 0;
        FileInputStream saveFile;
        try {
            saveFile = new FileInputStream("data.ser");
            ObjectInputStream save = null;
            try {
                save = new ObjectInputStream(new BufferedInputStream(saveFile));
                for (; ; ) {
                    newPages.add((Page) save.readObject());
                    count++;
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                save.close();
                saveFile.close();
            }
        } catch (EOFException e) {
            System.out.println("exception in write");
        } catch (Exception exc) {
            //System.out.println("exception in write");
        }
        this.pages = newPages;
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

            String command = inFromClient.readLine();
            StringTokenizer tokens = new StringTokenizer(command);
            tokens.nextToken(); // skip command

            if (command.startsWith("update")) {
                //TODO get pages from client and save them into the data.ser file also update self.pages
                ServerSocket welcomeData = new ServerSocket(12004);

                outToClient.writeBytes("12004\n");

                Socket dataSocket = welcomeData.accept();
                welcomeData.close();
                ArrayList<Page> newPages = new ArrayList<Page>();
                ObjectInputStream save = null;

                try {

                    save = new ObjectInputStream(new BufferedInputStream(dataSocket.getInputStream()));
                    for (; ; ) {
                        newPages.add((Page) save.readObject());
                    }
                } catch (Exception e) {
                    //System.out.println("error in loadPageList");
                    dataSocket.close();
                    if(save != null) {
                        save.close();
                    }
                }

                this.pages = newPages;

                //save current page list to sync accross server thread.
                saveCurrentPageList();

            } else if (command.startsWith("retr")) {
                //TODO send pages to client

                //load first to sync across server threads
                int port = Integer.parseInt(tokens.nextToken());
                loadPageList();
                Socket dataSocket = new Socket(connection.getInetAddress(), port);
                ObjectOutputStream objectOut = new ObjectOutputStream(dataSocket.getOutputStream());

                for(int i = 0; i < this.pages.size(); i++) {
                    objectOut.writeObject(this.pages.get(i));
                }
                dataSocket.close();
                objectOut.close();

            } else if (command.startsWith("images")){
                //TODO send images to client
                outToClient.writeBytes("12004\n");

                ServerSocket welcomeData = new ServerSocket(12004);
                Socket dataSocket  = welcomeData.accept();
                welcomeData.close();
                ArrayList<Image> newImages = new ArrayList<Image>();
                ObjectInputStream save = null;

                try {

                    save = new ObjectInputStream(new BufferedInputStream(dataSocket.getInputStream()));
                    for (; ; ) {
                        newImages.add((Image) save.readObject());
                    }
                } catch (Exception e) {
                    dataSocket.close();
                    if(save != null) {
                        save.close();
                    }
                }

                this.images = newImages;

                //save images into current image list to sync accross server thread.
                saveImageList();
            }
        }
    }  
    
    public void saveImageList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("./images", true);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(fileOut));
            for (int i = 0; i < images.size(); i++) {
                out.writeObject(images.get(i));
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("error in writing users");
        }
    }
    
}
