package cn.ruc.readio.ui.shelf;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

import java.util.Objects;

import cn.ruc.readio.R;

public class BookItem {
    private String BookName;
    private final String Author;
    private Bitmap Cover;
    private String CoverID;

    @NonNull
    public String getBookName() {
        return BookName;
    }
    public String getAuthor() {
        return Author;
    }
    public Bitmap getCover() {
        return Cover;
    }
    public String getCoverID() {
        return CoverID;
    }

    public BookItem(String BookName, String Author, String coverID) {
        //this.img=img;
        this.BookName = BookName;
        this.Author=Author;
        this.CoverID =coverID;
    }
    public BookItem(String BookName,String Author){
        this.BookName = BookName;
        this.Author=Author;
    }

    public void setBookName(String BookName){
        this.BookName = BookName;
    }
    public void setAuthor(String Author){
        this.BookName = Author;
    }
    public void setCover(Bitmap Cover){
        this.Cover = Cover;
    }
    public void setCoverID(String CoverID){
        this.CoverID = CoverID;
    }
}

