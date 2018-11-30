package Reddit;

import java.util.ArrayList;

public class PageList {
    private ArrayList<Page> pages;
    public void addPage(String title) {
    	Page newPage = new Page(title);
    	pages.add(newPage);
    }
    public ArrayList<Page> getPages(){
    	return pages;
    }
}
