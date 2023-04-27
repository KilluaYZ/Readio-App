package cn.ruc.readio.commendActivity;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import cn.ruc.readio.R;

import android.content.Intent;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

public class bookDetailActivity extends AppCompatActivity{

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookdetail);

        Intent intent = getIntent();
        String BookName = intent.getStringExtra("BookName");
        String Author = intent.getStringExtra("Author");
        String Abstract = "文化大革命如火如荼地进行,\n天文学家叶文洁在其间历经劫难，\n被带到军方绝秘计划'红岸工程'。\n叶文洁以太阳为天线，\n向宇宙发出地球文明的第一声啼鸣，\n取得了探寻外星文明的突破性进展。\n三颗无规则运行的太阳主导下，\n四光年外的'三体文明'百余次毁灭与重生，\n正被逼迫不得不逃离母星，\n而恰在此时，\n他们接收到了地球发来的信息。\n对人性绝望的叶文洁向三体人暴露了地球的坐标，\n彻底改变了人类的命运。\n 地球的基础科学出现了异常的扰动，\n纳米科学家汪淼进入神秘的网络游戏《三体》，\n开始逐步逼近这个世界的真相。\n汪淼参加一次玩家聚会时，\n接触到了地球上应对三体人到来而形成的一个秘密组织（ETO）。\n地球防卫组织中国区作战中心通过“古筝计划”，\n一定程度上挫败了拯救派和降临派扰乱人类科学界和其他领域思想的图谋，\n获悉处于困境之中的三体人为了得到一个能够稳定生存的世界决定入侵地球。\n 在运用超技术锁死地球人的基础科学之后，\n庞大的三体舰队开始向地球进发，\n人类的末日悄然来临。";
        String tag="科幻";
        String state="已完结";

        TextView textView1 = findViewById(R.id.book_name);
        TextView textView2 = findViewById(R.id.author);
        TextView textView3 = findViewById(R.id.book_tag);
        TextView textView4 = findViewById(R.id.book_state);
        TextView textView5 = findViewById(R.id.book_abstract);
        LinearLayout detail_page=findViewById(R.id.detail_page);
        ImageButton button = findViewById(R.id.return_commend);

        textView1.setText(BookName);
        textView2.setText(Author);
        textView3.setText(tag);
        textView4.setText(state);
        textView5.setText(Abstract);

        button.setOnClickListener(v -> finish());


        /*通过提取图片颜色，设置背景色*/
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.book_three_body);

        Palette p = Palette.from(myBitmap).generate();
        Palette.Swatch Swatch = p.getLightVibrantSwatch();

        int backgroundColor = R.color.white;

        if(Swatch != null){
            backgroundColor = Swatch.getRgb();
        }
        detail_page.setBackgroundColor(backgroundColor);
        detail_page.getBackground().setAlpha(30);



    }

}
