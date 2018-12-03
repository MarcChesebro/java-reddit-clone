package Reddit;

import java.awt.Image;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {
    private String postText;
    private String imagePath;
    private ArrayList<Comment> comments;

    public Post(String postText, String image) {
        this.postText = postText;
        this.imagePath = image;
        this.comments = new ArrayList<Comment>();
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPostText() {
        return this.postText;
    }

    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    public void addComment(String user, String text) {
        Comment comment = new Comment(user, text);
        comments.add(comment);
    }
}
