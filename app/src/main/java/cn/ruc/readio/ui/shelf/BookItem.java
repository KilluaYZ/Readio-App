package cn.ruc.readio.ui.shelf;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class BookItem {
    private String title;

    private int pic;

    @NonNull
    public String getTitle() {
        return title;
    }
    public int getPic() {
        return pic;
    }

    public BookItem(String title,int coverID) {
        //this.img=img;
        this.title = title;
        this.pic=coverID;
    }

    public void setTitle(String title){
        this.title=title;
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
