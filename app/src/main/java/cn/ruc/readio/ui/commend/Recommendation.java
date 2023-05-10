package cn.ruc.readio.ui.commend;

import androidx.annotation.NonNull;
import java.util.Objects;

public class Recommendation {

    private final String quote;
    private final String author;
    private final String book_name;

    @NonNull
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

        if(Objects.equals(author, "null") && !Objects.equals(book_name, "null")) {
            return book_name;
        }
        else if (Objects.equals(book_name, "null") && !Objects.equals(author, "null")) {
            return author;
        }
        else if(Objects.equals(book_name, "null") && Objects.equals(author, "null")){
            return "null";
        }
        else return author + book_name;
    }

    public Recommendation(String quote, String author,String book_name) {

        this.quote = quote;
        this.author = author;
        this.book_name =book_name;
    }
}
