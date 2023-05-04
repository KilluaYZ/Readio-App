package cn.ruc.readio.ui.shelf;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class BookItem {
    private Bitmap cover;
    private String title;
    @NonNull
    public Bitmap getCover() {
        return cover;
    }
    public String getTitle() {
        return title;
    }

    public BookItem(Bitmap cover, String title) {
        //this.img=img;
        this.cover = cover;
        this.title = title;
    }
}
