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
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.awt.Color;

public class RedditFrontPage extends JFrame implements ActionListener{
	
	private JButton next, prev;
    private JTextField pId, textSize, dataSize;
    //private JPanel frontPage;
    private ArrayList<Page> pages; //WILL BE PAGES
	
    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem homePage; 
    JMenuItem createPage;
    GridBagConstraints loc;
	public RedditFrontPage() {
		pages = getPageListFromServer();
		showFrontPage();
		//for (int i=0; i<10; i++) {
		//	pages.add(i);
		//}
	    //setLayout(new GridBagLayout());
	    //loc = new GridBagConstraints();
	    
	    //commands = new ArrayList<Command>();
	    //commandInd = 0;
		//loc.anchor = GridBagConstraints.WEST;
		//loc.insets = new Insets(8,8,8,8);
		
		//this.prev = new JButton("Prev");
	    //buttonPanel.add(this.prev, loc);
	    
	    //this.next = new JButton("Next");
	    //buttonPanel.add(this.next, loc);
	    

	    
	    //loc.gridx = 0;
	    //loc.gridy = 0;
	    
	    //GridLayout experimentLayout = new GridLayout(2,3);
	    //JPanel textPanel = new JPanel();	
	    //textPanel.setLayout(experimentLayout);
		
	    //textPanel.add(new JLabel("Process ID"), loc);
	    //textPanel.add(new JLabel("Text Size"), loc);
	    //textPanel.add(new JLabel("Data Size"), loc);
	    
	    //pId = new JTextField(10);
	    //textPanel.add(pId,loc);
	    
	    //textSize = new JTextField(10);
	    //textPanel.add(textSize,loc);
	    
	    
	    //dataSize = new JTextField(10);
	    //textPanel.add(dataSize,loc);
	    
	    //loc.gridy = 1;
	    //this.add(textPanel,loc);
	    
	    //GridLayout experimentLayout3 = new GridLayout(9,1);
		//JPanel framePanel = new JPanel();	
		//framePanel.setLayout(experimentLayout3);
	    
	
	    //loc.gridx = 0;
	    //loc.gridy = 0;
	    //loc.insets.bottom = 20;
	    //JLabel temp = new JLabel("Physical Memory");
	    //temp.setHorizontalAlignment(JLabel.CENTER);
	    //framePanel.add(temp, loc);
	    
	    //loc.insets.bottom = 0;
	    //loc.insets.top = 0;
	    //for(int i = 0; i<8;i++){
		//    loc.gridx = 0;
	    //	loc.gridy = i + 1;
	    //	JButton btn = new JButton("Empty");
	    //	btn.setPreferredSize(new Dimension(200, 40));
		//	this.frameButtons.add(i,btn);
	    //	framePanel.add(btn,loc);
	    //}
		
	    //loc.anchor = GridBagConstraints.NORTHWEST;
		//loc.weighty = 0;
		//loc.weightx = 1;
		//loc.gridheight = 2;
	    //loc.gridx = 0;
	    //loc.gridy = 2;
	    //this.add(framePanel,loc);
	    fileMenu = new JMenu("File");
	    homePage = new JMenuItem("HomePage");
	    createPage = new JMenuItem("Create Page");
	    quitItem = new JMenuItem("Quit");
	    
	    
	    fileMenu.add(homePage);
	    fileMenu.add(createPage);
	    fileMenu.add(quitItem);

	    
	    menus = new JMenuBar();
	    setJMenuBar(menus);
	    menus.add(fileMenu);
	
	    //this.next.addActionListener(this);
	    //this.prev.addActionListener(this);
	    this.fileMenu.addActionListener(this);
	    this.quitItem.addActionListener(this);
	    this.homePage.addActionListener(this);
	}
	public void showFrontPage(){
		Container container = this.getContentPane();
		container.removeAll();
		GridLayout fPageLayout = new GridLayout(pages.size(), 1);
	    JPanel frontPage = new JPanel();	
	    frontPage.setLayout(fPageLayout);
	    
	    for(int i = 0; i<pages.size(); i++){
	    	JPanel preview = new JPanel();
	    	int totalItems = 1;
	    	String imgPath = "";
	    	boolean isFirstPost = pages.get(i).getFirstPost() != null;
	    	if (isFirstPost) {
	    		totalItems++;
	    		imgPath = pages.get(i).getFirstPost().getImagePath();
	    		if (imgPath != "") totalItems++;
	    	}
		    GridLayout previewLayout = new GridLayout(totalItems, 1);
	    	preview.setLayout(previewLayout);
	    	//Go to Page button brings to page
	    	final JButton goToPage = new JButton(pages.get(i).getTitle());
	    	final int pageNum = i;
	    	preview.add(goToPage);//Add latest post's label and image
	    	goToPage.addActionListener(new ActionListener() 
	        {
	            @Override
	            public void actionPerformed(ActionEvent event) 
	            {
	            	showPage(pages.get(pageNum));
	            }
	        });
	    	if (isFirstPost) {
	    		preview.add(new JLabel(pages.get(i).getFirstPost().getPostText()));
	    		//creates the image panel based on the path to the image(possibly keep all images in a folder

	    		if (imgPath != "") {
	    			preview.add(new ImagePanel(imgPath));
	    		}
	    	}
	    	
	    	frontPage.add(preview);	    	
	    	//frontPage.add(new JTextField(pages.get(i).toString()));
	    }	
	    JScrollPane pane = new JScrollPane();
	    pane.setViewportView(frontPage);
        container.add(pane);
        container.validate();
        container.repaint();
	}
	public void showPage(Page page) {
		Container container = this.getContentPane();
		container.removeAll();
		JPanel newPage = new JPanel();
		final JButton goToFrontPage = new JButton("Back to Front Page");
    	goToFrontPage.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	showFrontPage();
            }
        });
    	newPage.add(goToFrontPage);
	    JScrollPane pane = new JScrollPane();
	    pane.setViewportView(newPage);
        container.add(pane);
        container.validate();
        container.repaint();
        //System.out.println("in4");
        setVisible(true);
	}
	public ArrayList<Page> getPageListFromServer() {
		ArrayList <Page> retList = new ArrayList<Page>();
		//retList will be the page list return from the server.
		for (int i = 0; i < 30; i++) {
			Page p = new Page("NewPage" + i);
			p.addPost("hello world!", "");
			p.addPost("newest POST!!!", "");
			retList.add(p);
		}
		return retList;
	}
    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was fired
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){
    
    }
}
