package Reddit;

import java.util.ArrayList;

public class PageList {
    private ArrayList<Page> pages;

    public void addPage(String title) {
        Page newPage = new Page(title);
        pages.add(newPage);
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public Page getPage(String pageTitle) {

        for (int i = 0; i < this.pages.size(); i++) {
            if (this.pages.get(i).getTitle().equals(pageTitle)) {
                return this.pages.get(i);
            }
        }
        return null;
    }
}
