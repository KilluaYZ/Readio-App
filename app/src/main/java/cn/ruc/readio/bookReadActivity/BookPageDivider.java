package cn.ruc.readio.bookReadActivity;

import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;

public class BookPageDivider {
    book mbook;
    private ArrayList<String> pagesList = new ArrayList<>();
    private String content = "";
    private int numbersPerPage = 0;

    public BookPageDivider(book mbook, int pageSize){
        this.mbook = mbook;
        ArrayList<Pair<String, String>> chapter =  mbook.getContent();
        chapter.forEach((item) -> {
            content += item.second;
        });
        this.numbersPerPage = pageSize;
        splitPages();
    }

    public ArrayList<String> getPagesList() {
        return pagesList;
    }

    public void splitPages(){
        int maxPageSize = (int)(this.content.length() / this.numbersPerPage);
        for(int i = 0;i < maxPageSize;++i){
            this.pagesList.add(this.content.substring(i* numbersPerPage, (i+1) * numbersPerPage));
        }
        this.pagesList.add(this.content.substring(maxPageSize*maxPageSize, this.content.length()));
    }


}
