package Reddit;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.io.File;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.Color;

public class RedditFrontPage extends JFrame implements ActionListener{
	
	private JButton next, prev;
    private JTextField pId, textSize, dataSize;
    //private JPanel frontPage;
    private ArrayList<Page> pages; //WILL BE PAGES
    private String handle;
    private Object refreshObj;
	
    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem refresh; 
    JMenuItem createPage;
	public RedditFrontPage() {
		handle = (String)JOptionPane.showInputDialog(
                this,
                "Welcome to a GVSU Social Media platform, \n"
                + "What would you like to be called in this session?",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Anonymous");
		if (handle == null || handle == "") handle = "Anonymous";
		
		pages = getPageListFromServer();
		showFrontPage();
		
	    fileMenu = new JMenu("File");
	    refresh = new JMenuItem("Refresh");
	    createPage = new JMenuItem("Create Page");
	    quitItem = new JMenuItem("Quit");
	    fileMenu.add(refresh);
	    fileMenu.add(createPage);
	    fileMenu.add(quitItem);
	    menus = new JMenuBar();
	    setJMenuBar(menus);
	    menus.add(fileMenu);
	    
	    this.refresh.addActionListener(this);
	    this.fileMenu.addActionListener(this);
	    this.quitItem.addActionListener(this);
	    this.createPage.addActionListener(this);
	}
	public void showFrontPage(){
		refreshObj = null;
		Container container = this.getContentPane();
		container.removeAll();
		
	    JPanel frontPage = new JPanel();	
		GridBagLayout fPageLayout = new GridBagLayout();
		GridBagConstraints loc = new GridBagConstraints();
	    frontPage.setLayout(fPageLayout);
	    
		loc.weightx = 1;
		loc.fill = GridBagConstraints.HORIZONTAL;
	    for(int i = 0; i<pages.size(); i++){
	    	JPanel preview = new JPanel();
	    	String imgPath = "";
	    	boolean isFirstPost = pages.get(i).getFirstPost() != null;
	    	if (isFirstPost) {
	    		imgPath = pages.get(i).getFirstPost().getImagePath();
	    	}
		    GridBagLayout previewLayout = new GridBagLayout();
	    	preview.setLayout(previewLayout);
	    	//Go to Page button brings to page
	    	final JButton goToPage = new JButton(pages.get(i).getTitle());
	    	final int pageNum = i;
	    	loc.gridy = 0;
	    	preview.add(goToPage, loc);//Add latest post's label and image
	    	goToPage.addActionListener(new ActionListener() 
	        {
	            @Override
	            public void actionPerformed(ActionEvent event) 
	            {
	            	showPage(pages.get(pageNum));
	            }
	        });
	    	if (isFirstPost) {
	    		loc.gridy = 1;
	    		preview.add(new JLabel(pages.get(i).getFirstPost().getPostText()), loc);
	    		//creates the image panel based on the path to the image(possibly keep all images in a folder

	    		if (imgPath != "") {
	    			loc.gridy = 2;
		    		ImagePanel img = new ImagePanel(imgPath);
	    			loc.ipady = img.getHeight();
	    			preview.add(img, loc);
	    			loc.ipady = 0;
	    		}
	    	}
	    	loc.gridy = i;
	    	frontPage.add(preview, loc);	    	
	    	//frontPage.add(new JTextField(pages.get(i).toString()));
	    }
	   // frontPage.setBackground(Color.CYAN);
	    //frontPage.setBorder(BorderFactory.createLineBorder(Color.black));
	    JScrollPane pane = new JScrollPane();
	    pane.setViewportView(frontPage);
        container.add(pane);
        container.validate();
        container.repaint();
	}
	public void showPage(Page page) {
		refreshObj = page;
		Container container = this.getContentPane();
		container.removeAll();
		
		JPanel newPage = new JPanel();
		GridBagLayout pageLayout = new GridBagLayout();
		GridBagConstraints loc = new GridBagConstraints();
		newPage.setLayout(pageLayout);
		loc.fill = GridBagConstraints.HORIZONTAL;
		loc.anchor = GridBagConstraints.NORTH;
		loc.weightx = 0;
		loc.gridy = 0;
		loc.gridx = 0;
		final JButton goToFrontPage = new JButton("Back to Front Page");
    	goToFrontPage.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	showFrontPage();
            }
        });

    	newPage.add(goToFrontPage, loc);
		loc.gridy = 1;
		loc.weightx = 1;
		JLabel pageTitleLabel = new JLabel("Welcome to " + page.getTitle());
		pageTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		pageTitleLabel.setFont(new Font(pageTitleLabel.getName(), Font.PLAIN, 40));
		pageTitleLabel.setForeground(Color.white);
    	newPage.add(pageTitleLabel, loc);
    	
    	JButton addPost = new JButton("Create New \n Post");
    	addPost.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	String s = (String)JOptionPane.showInputDialog(
	                    addPost,
	                    "Add Post Comment",
	                    "Customized Dialog",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "Empty");
            	if (s == null || s == "") s = "Empty";
            	File directory = new File("./Images");
                if (! directory.exists()){
                    directory.mkdir();
                }
            	FileDialog fd = new FileDialog(new JFrame());
            	fd.setVisible(true);
            	File[] f = fd.getFiles();
            	if (f.length == 0) return;
            	Path imgPath = Paths.get(fd.getFiles()[0].getAbsolutePath());
            	Path dest = Paths.get("./Images/"+fd.getFiles()[0].getName());
                
            	try {
            		Files.copy(imgPath, dest, StandardCopyOption.REPLACE_EXISTING);
            	} catch (IOException e) {
            		System.out.println("Error in posting your Image.");
            	}
            	page.addPost(s, dest.toString());
            	updateServer();
            	showPage(page);
            }
        });
    	loc.fill = GridBagConstraints.NONE;
    	loc.gridy = 2;
    	loc.weightx = 0;
    	loc.anchor = GridBagConstraints.NORTHWEST;
    	newPage.add(addPost, loc);
    	loc.anchor = GridBagConstraints.CENTER;
    	loc.fill = GridBagConstraints.HORIZONTAL;
    	loc.weightx = 1;
    	for (int i = 0; i < page.getPosts().size(); i ++) {
    		int count;
    		Post post = page.getPosts().get(i);
    		if (post.getImagePath() != "") {
    			count = 3;
    		} else {
    			count = 2;
    		}
    		JPanel postPanel = new JPanel();
    		GridBagLayout postLayout = new GridBagLayout();
    		postPanel.setLayout(postLayout);
    		loc.gridy = 0;
    		JLabel label = new JLabel(post.getPostText());
    		//label.setFont(new Font(label.getName(), Font.PLAIN, 20));
    		postPanel.add(label, loc);
    		if (count == 3) {
	    		loc.gridy = 1;
	    		ImagePanel img = new ImagePanel(post.getImagePath());
	    		loc.ipady = img.getHeight();
    			postPanel.add(img, loc);
    		}
    		final JTextArea commentView = new JTextArea();
    		for (int j = 0; j < post.getComments().size(); j++) {
    			Comment comment = post.getComments().get(j);
    			String commentStr = comment.getUser() +": " + comment.getText() + "\n";
    			commentView.setText(commentView.getText() + commentStr);
    		}
    		JScrollPane commentScroll = new JScrollPane();
    		commentScroll.setViewportView(commentView);
    		loc.gridy = 3;
    		loc.ipady = 150 - (post.getComments().size() * 16);
    		postPanel.add(commentScroll, loc);
    		
    		final JTextField addCommentField = new JTextField();
    		loc.gridy = 4;
    		loc.ipady = 0;
    		postPanel.add(addCommentField, loc);
    		
    		final JButton submitButton = new JButton("Comment");
    		loc.gridx = 1;
    		loc.weightx = 0;
    		postPanel.add(submitButton, loc);
	    	submitButton.addActionListener(new ActionListener() 
	        {
	            @Override
	            public void actionPerformed(ActionEvent event) 
	            {
	            	String commentText = addCommentField.getText();
	            	commentView.setText(commentView.getText() +
	            			handle + ": " + commentText + "\n");
	            	addCommentField.setText("");
	            	post.addComment(handle, commentText);
	            	updateServer();
	            }
	        });
	    	loc.weightx = 1;
    		loc.gridx = 0;
    		loc.gridy = i + 3;
    		loc.insets = new Insets(40, 0, 0, 0);
    		newPage.add(postPanel, loc);
    		loc.insets = new Insets(0, 0, 0, 0);
    	}
	    JScrollPane pane = new JScrollPane();
		newPage.setBackground(Color.DARK_GRAY);
	    pane.setViewportView(newPage);
        container.add(pane);
        container.validate();
        container.repaint();
        setVisible(true);
	}
	public ArrayList<Page> getPageListFromServer() {
		//FIXME pull from the server.
		
		
		//THIS IS TEST DATA
		ArrayList <Page> retList = new ArrayList<Page>();
		//retList will be the page list return from the server.
		for (int i = 0; i < 5; i++) {
			Page p = new Page("NewPage" + i);
			p.addPost("hello world 1112341234!", "");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.getPosts().get(0).addComment("test", "test111");
			p.addPost("hello world 1234!", "");
			p.addPost("hello world 5!", "./test.png");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("hello world 5!", "");
			p.addPost("newest POST!!!", "./test.png");
			retList.add(p);
		}
		return retList;
	}
	public void updateServer() {
		//FIXME Update servers, this is called when a comment, post, or new page is added.
	}
    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was fired
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){
        JComponent buttonPressed = (JComponent) e.getSource();
        if (buttonPressed == this.createPage) {
        	String s = (String)JOptionPane.showInputDialog(
    	                    this,
    	                    "Please name your page!",
    	                    "Customized Dialog",
    	                    JOptionPane.PLAIN_MESSAGE,
    	                    null,
    	                    null,
    	                    "Default Page Name");
        	this.pages = getPageListFromServer();
        	for (int i = 0; i < pages.size(); i++) {
        		System.out.println(pages.get(i).getTitle() + " " + s);
        		if (pages.get(i).getTitle().toLowerCase().equals(s.toLowerCase())) {
        			JOptionPane.showMessageDialog(this, "That Page Name already exists.");
        			return;
        		}
        	}
        	if (s != null && s != "") {
        		pages.add(new Page(s));
        		updateServer();
        		showFrontPage();
        	}
        } else if (buttonPressed == refresh) {
        	this.pages = getPageListFromServer();
        	if (refreshObj == null) {
        		showFrontPage();
        	} else {
        		Page pageToRefresh = (Page)refreshObj;
        		for (int i = 0; i < pages.size(); i++) {
        			if (pages.get(i).getTitle().toLowerCase().equals(pageToRefresh.getTitle().toLowerCase())) {
        				showPage(pages.get(i));
        			}
        		}
        	}
        }
    }
}
