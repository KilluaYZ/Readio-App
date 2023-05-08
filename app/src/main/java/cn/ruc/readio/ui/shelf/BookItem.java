package cn.ruc.readio.ui.shelf;

import androidx.annotation.NonNull;

public class BookItem {
    private String BookName;
    private String Author;
    private int pic;

    @NonNull
    public String getBookName() {
        return BookName;
    }
    public String getAuthor() {
        return Author;
    }
    public int getPic() {
        return pic;
    }

    public BookItem(String BookName, String Author, int coverID) {
        //this.img=img;
        this.BookName = BookName;
        this.Author=Author;
        this.pic=coverID;
    }

    public void setBookName(String BookName){
        this.BookName = BookName;
    }
    public void setAuthor(String Author){
        this.BookName = Author;
    }
    public void setPic(int id){this.pic = id;}
}

/*public class BookItem {
    private Bitmap cover;
    private String title;
    private String coverID;

    private int pic;

    @NonNull
    public Bitmap getCover() {
        return cover;
    }
    public String getTitle() {
        return title;
    }
    public String getCoverID() {
        return coverID;
    }
    public int getPic() {
        return pic;
    }

    public BookItem(Bitmap cover, String title,String coverID) {
        //this.img=img;
        this.cover = cover;
        this.title = title;
        this.coverID=coverID;
    }

    public BookItem(String title,int coverID) {
        //this.img=img;
        this.title = title;
        this.pic=coverID;
    }


    public BookItem(){
        title=" ";
    }

    public void setCover(Bitmap cover){
        this.cover=cover;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public void setCoverID(String id){this.coverID = id;}
}*/
