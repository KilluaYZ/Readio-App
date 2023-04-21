package cn.ruc.readio.ui.commend;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class DataBean {

    //private final Bitmap img;
    private final String quote;
    private final String source;


    @NonNull
    //public Bitmap getimg() {return img;}
    public String getAutor() {
        return quote;
    }
    public String getSource() {
        return source;
    }

    public DataBean(String msg, String source) {
        //this.img=img;
        this.quote = msg;
        this.source = source;
    }
}
