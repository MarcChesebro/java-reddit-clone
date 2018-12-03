package Reddit;

import java.awt.*;
import java.util.ArrayList;

public class Page {
    private String title;
    private ArrayList<Post> posts;

    public Page(String title) {
        this.title = title;
        this.posts = new ArrayList<Post>();
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost(String postText, String img) {
        Post newPost = new Post(postText, img);
        this.posts.add(0, newPost);
    }

    public Post getFirstPost() {
        if (posts.size() > 0) {
            return posts.get(0);
        } else {
            return null;
        }
    }

}
