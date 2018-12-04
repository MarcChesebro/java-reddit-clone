package Reddit;

import javax.swing.JFrame;

public class RedditStart {
	public static void main(String[] args){
        RedditFrontPage gui = new RedditFrontPage();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("GVSU Talk");
        gui.setSize(700, 800);
        gui.setVisible(true);
	}
}
