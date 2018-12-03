package Reddit;

import java.io.Serializable;
import java.util.ArrayList;

public class Comment implements Serializable {
    private String user;
    private String text;

    public Comment(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
}

