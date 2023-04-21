package cn.ruc.readio.commendActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import cn.ruc.readio.R;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class bookDetailActivity extends AppCompatActivity{

    private TextView textView1,textView2;
    private Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookdetail);
        Intent intent = getIntent();
        String first = intent.getStringExtra("Quote");
        String second = intent.getStringExtra("Source");
        textView1 = findViewById(R.id.quote);
        textView2 = findViewById(R.id.source);
        button = findViewById(R.id.return_commend);
        textView1.setText(first);
        textView2.setText(second);
        //imageView.setOnClickListener(view -> Toast.makeText(bookDetailActivity.this,first,Toast.LENGTH_SHORT).show());
        button.setOnClickListener(v -> finish());
    }
}
