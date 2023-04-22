package cn.ruc.readio.ui.commend;

import androidx.annotation.NonNull;

public class Recommendation {

    //private final Bitmap img;
    private final String quote;
    private final String source;

    @NonNull
    //public Bitmap getImg() {return img;}
    public String getQuote() {
        return quote;
    }
    public String getSource() {
        return source;
    }

    public Recommendation(String quote, String source) {
        //this.img=img;
        this.quote = quote;
        this.source = source;
    }
}
