package cn.ruc.readio.bookReadActivity;

import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import cn.ruc.readio.R;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class readBookActivity extends Activity {
    private String BookID,BookName,Author;
    private book my_book=new book();
    private TextView textView1;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        /*接受传递的消息*/
        Intent intent = getIntent();

        /*获取内容*/
        BookName = intent.getStringExtra("BookName");
        Author = intent.getStringExtra("Author");
        BookID=intent.getStringExtra("BookID");
        Log.d("book", BookID);

        /*设置内容*/
        getBook();
        textView1 = findViewById(R.id.book_content);
        //textView1.setText(BookID);
        textView1.setText(BookID);
    }

    void getBook(){
        HttpUtil.getRequestAsyn("/app/books/reading/"+4, new ArrayList<>() ,new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Tools.my_toast(readBookActivity.this,"请求异常，加载不出来");
            }
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject bookinfo = jsonObject.getJSONObject("data");

                    my_book.setBookId(bookinfo.getString("id"));
                    my_book.setBookAbstract(bookinfo.getString("abstract"));
                    my_book.setBookName(bookinfo.getString("bookName"));
                    my_book.setAuthorName(bookinfo.getString("authorName"));
                    my_book.setSize(bookinfo.getInt("size"));

                    JSONArray content=bookinfo.getJSONArray("content");
                    ArrayList<Pair<String, String>> bookcontent=new ArrayList<>();

                    for(int i = 0;i<content.length();i++){
                        JSONObject contenti= content.getJSONObject(i);
                        bookcontent.add(Pair.create(contenti.getString("ChapterName"),contenti.getString("Text")));
                    }
                    my_book.setContent(bookcontent);
                    readBookActivity.this.runOnUiThread(() -> textView1.setText(my_book.getChapter(0)));
                } catch (JSONException e) {
                    Tools.my_toast(readBookActivity.this, "子评论数量加载失败");
                }
            }
        });
    }

}