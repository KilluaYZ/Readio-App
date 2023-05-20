package cn.ruc.readio.bookReadActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cn.ruc.readio.R;

public class readBookActivity extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        /*接受传递的消息*/
        Intent intent = getIntent();

        /*获取内容*/
        String BookName = intent.getStringExtra("BookName");
        String Author = intent.getStringExtra("Author");

        /*设置内容*/
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView textView1 = findViewById(R.id.book_content);
        textView1.setText(BookName+Author);

    }
}