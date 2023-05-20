package cn.ruc.readio.bookReadActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import cn.ruc.readio.R;
import cn.ruc.readio.ui.userpage.User;
import cn.ruc.readio.worksActivity.PieceComments;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class bookDetailActivity extends AppCompatActivity{

    private String BookName;
    private String Author;
    private String Abstract;
    private List<PieceComments> comment_list;
    private TextView textView1,textView2,textView3;
    private RelativeLayout detail_page;
    private View view;

    private RecyclerView recycler_view;
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookdetail);

        /*接受传递的消息*/
        Intent intent = getIntent();

        /*获取内容*/
        BookName = intent.getStringExtra("BookName");
        Author = intent.getStringExtra("Author");
        Abstract = "文化大革命如火如荼地进行,\n天文学家叶文洁在其间历经劫难，\n被带到军方绝秘计划'红岸工程'。\n叶文洁以太阳为天线，\n向宇宙发出地球文明的第一声啼鸣，\n取得了探寻外星文明的突破性进展。\n三颗无规则运行的太阳主导下，\n四光年外的'三体文明'百余次毁灭与重生，\n正被逼迫不得不逃离母星，\n而恰在此时，\n他们接收到了地球发来的信息。\n对人性绝望的叶文洁向三体人暴露了地球的坐标，\n彻底改变了人类的命运。\n 地球的基础科学出现了异常的扰动，\n纳米科学家汪淼进入神秘的网络游戏《三体》，\n开始逐步逼近这个世界的真相。\n汪淼参加一次玩家聚会时，\n接触到了地球上应对三体人到来而形成的一个秘密组织（ETO）。\n地球防卫组织中国区作战中心通过“古筝计划”，\n一定程度上挫败了拯救派和降临派扰乱人类科学界和其他领域思想的图谋，\n获悉处于困境之中的三体人为了得到一个能够稳定生存的世界决定入侵地球。\n 在运用超技术锁死地球人的基础科学之后，\n庞大的三体舰队开始向地球进发，\n人类的末日悄然来临。";

        /*设置内容*/
        initdetail();

        /*通过提取图片颜色，设置背景色*/
        setBackgroundColor();

        /*设置返回按钮*/
        ImageButton re_button = findViewById(R.id.return_commend);
        re_button.setOnClickListener(v -> finish());

        /*设置阅读按钮*/
        Button read_book=findViewById(R.id.read);
        read_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent read_intent=new Intent(bookDetailActivity.this,readBookActivity.class);
                read_intent.putExtra("BookName",BookName);
                read_intent.putExtra("Author",Author);
                startActivity(read_intent);
            }
        });

        /*设置加入书架按钮*/
        Button add_shelf=findViewById(R.id.add_bookmark);


        /*设置评论部分*/
        /*添加下划线*/
        TextView tv=findViewById(R.id.more_comment);
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        /*添加跳转到所有评论界面链接*/
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent comment_intent=new Intent(bookDetailActivity.this,allCommentActivity.class);
                comment_intent.putExtra("BookName",BookName);
                comment_intent.putExtra("Author",Author);
                startActivity(comment_intent);
            }
        });
        /*设置卡片中的评论内容*/
        SetComments();

    }

    /*设置内容*/
    private void initdetail(){

        textView1 = findViewById(R.id.book_name);
        textView2 = findViewById(R.id.author);
        textView3 = findViewById(R.id.book_abstract);

        textView1.setText(BookName);
        textView2.setText(Author);
        textView3.setText(Abstract);
    }
    @SuppressLint("ResourceAsColor")
    private void setBackgroundColor() {
        detail_page=findViewById(R.id.detail_page);
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

    /*获取评论内容*/
    private void initComments() {

        comment_list = new ArrayList<>();

        User user = new User("呆头鹅","2020201694@ruc.edu.cn","123456");

        comment_list.add(new PieceComments("好棒\n好棒",12,user));
        comment_list.add(new PieceComments("好棒",12,user));
        comment_list.add(new PieceComments("好棒",12,user));
        comment_list.add(new PieceComments("好棒",12,user));
    }


    private void initView() {
        recycler_view = findViewById(R.id.book_comment);
    }


    private void SetComments(){
        view = findViewById(R.id.comments_part);
        Context context = view.getContext();

        initComments();
        initView();

        LinearLayoutManager m=new LinearLayoutManager(this);
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(m);
        bookCommentAdapter myAdapter = new bookCommentAdapter(context,comment_list);
        recycler_view.setAdapter(myAdapter);

    }

}
