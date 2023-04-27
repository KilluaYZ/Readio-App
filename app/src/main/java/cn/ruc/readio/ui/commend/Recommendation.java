package cn.ruc.readio.ui.commend;

import androidx.annotation.NonNull;

public class Recommendation {

    //private final Bitmap img;
    private final String quote;
    private final String author;
    private final String book_name;

    @NonNull
    //public Bitmap getImg() {return img;}
    public String getQuote() {
        return quote;
    }
    public String getAuthor() {
        return author;
    }
    public String getBookName() {
        return book_name;
    }
    public String getSource() {
        return author + book_name;
    }

    public Recommendation(String quote, String author,String book_name) {
        //this.img=img;
        this.quote = quote;
        this.author = author;
        this.book_name =book_name;
    }
}
