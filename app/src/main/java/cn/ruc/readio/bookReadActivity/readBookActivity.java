package cn.ruc.readio.bookReadActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.ruc.readio.R;

public class readBookActivity extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }  //用于调整状态栏为透明色

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