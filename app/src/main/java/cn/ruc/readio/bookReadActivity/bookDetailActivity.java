package cn.ruc.readio.bookReadActivity;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
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
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import cn.ruc.readio.worksActivity.PieceComments;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class bookDetailActivity extends AppCompatActivity{

    private String BookName,Author;
    private int BookID;
    private List<PieceComments> comment_list=new ArrayList<>();
    private TextView book_name, author, book_abstract,view_count,length;
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
        BookID = intent.getIntExtra("BookID",0);

        /*设置内容*/
        initdetail();

        /*从服务器中获取数据*/
        refreshData();

        /*通过提取图片颜色，设置背景色*/
        setBackgroundColor();

        /*设置返回按钮*/
        ImageButton re_button = findViewById(R.id.return_commend);
        re_button.setOnClickListener(v -> finish());

        /*设置阅读按钮*/
        Button read_book=findViewById(R.id.read);
        read_book.setOnClickListener(view -> {
            Intent read_intent=new Intent(bookDetailActivity.this,readBookActivity.class);
            read_intent.putExtra("BookName",BookName);
            read_intent.putExtra("Author",Author);
            read_intent.putExtra("BookID",BookID);
            startActivity(read_intent);
        });

        /*设置加入书架按钮*/
        Button add_shelf=findViewById(R.id.add_bookmark);


        /*设置评论部分*/
        /*添加下划线*/
        TextView tv=findViewById(R.id.more_comment);
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        /*添加跳转到所有评论界面链接*/
        tv.setOnClickListener(view -> {
            if(comment_list==null)
            {
                Toast.makeText(bookDetailActivity.this,"没有评论啦~",Toast.LENGTH_SHORT).show();
                /*Intent comment_intent = new Intent(bookDetailActivity.this, allCommentActivity.class);
                comment_intent.putExtra("BookName", BookName);
                comment_intent.putExtra("Author", Author);
                comment_intent.putExtra("BookID", BookID);
                startActivity(comment_intent);*/
            }
            else {
                Intent comment_intent = new Intent(bookDetailActivity.this, allCommentActivity.class);
                comment_intent.putExtra("BookName", BookName);
                comment_intent.putExtra("Author", Author);
                comment_intent.putExtra("BookID", BookID);
                startActivity(comment_intent);
            }
        });
        /*设置卡片中的评论内容*/
        refreshCommentData();
        SetComments();
    }

    /*设置内容*/
    private void initdetail(){

        book_name = findViewById(R.id.book_name);
        author = findViewById(R.id.author);
        book_abstract = findViewById(R.id.book_abstract);
        view_count=findViewById(R.id.views);
        length=findViewById(R.id.words);

        book_name.setText(BookName);
        author.setText(Author);
        book_abstract.setText("这里是书籍简介~");
    }
    @SuppressLint("ResourceAsColor")
    private void setBackgroundColor() {
        detail_page=findViewById(R.id.detail_page);
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_cover2);

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

        //initComments();
        if(comment_list==null)
        {
            TextView no_comment=findViewById(R.id.no_comment);
            no_comment.setText("这本书暂时没有评论哦~\n来当第一位吧！");
            no_comment.setGravity(Gravity.CENTER);
        }

        LinearLayoutManager m=new LinearLayoutManager(bookDetailActivity.this);
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view = findViewById(R.id.book_comment);
        recycler_view.setLayoutManager(m);
        bookCommentAdapter myAdapter = new bookCommentAdapter(context,comment_list);
        recycler_view.setAdapter(myAdapter);

    }
    public void refreshData(){
        HttpUtil.getRequestAsyn("/app/book/"+ BookID, new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mtoast("请求异常，加载不出来");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    /*获取所有书籍信息*/
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");

                    /*取出是否加入书架*/
                    String added=data.getString("added");

                    /*取出书籍自身信息部分*/
                    JSONObject book_info=data.getJSONObject("book_info");
                    book_name.setText(book_info.getString("bookName"));
                    length.setText(book_info.getString("length"));
                    view_count.setText(book_info.getString("views"));
                    String likes=book_info.getString("likes");
                    String shares=book_info.getString("shares");
                    book_abstract.setText(book_info.getString("abstract"));

                    /*取出书籍评论*/
                    /*JSONObject book_comments=data.getJSONObject("comments");
                    int comment_size=book_comments.getInt("size");
                    JSONArray comments_list=book_comments.getJSONArray("data");
                    if(comment_size==0)
                    {
                        comment_list=null;
                    }
                    if(comment_size!=0){
                        for(int i = 0; i < comment_size; i++){
                            JSONObject comment_item = comments_list.getJSONObject(i);

                            User user = new User("小美","20230522@ruc.edu.cn", "12345678");
                            //user.setUserName("用户1");
                            user.setAvaID(String.valueOf(R.drawable.juicy_orange_smile_icon));

                            PieceComments comment = new PieceComments(comment_item.getString("content"),comment_item.getInt("likes"),user);
                            comment_list.add(comment);
                        }
                        *//*new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0;i < comment_list.size(); ++i){
    //                                Bitmap pic = HttpUtil.getAvaSyn(works.get(i).getUser().getAvaID());
                                    Bitmap pic = null;
                                    try {
                                        pic = Tools.getImageBitmapSyn(bookDetailActivity.this,comment_list.get(i).getUser().getAvaID());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    } catch (JSONException | ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    comment_list.get(i).getUser().setAvator(pic);
                                    Log.d("bookcommentadpter","需要更新");
                                }

                            }
                        }).run();*//*
                    }

                    runOnUiThread(new Runnable() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void run() {
                            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.book_comment);
                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                        }
                    });*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void mtoast(String msg){
        runOnUiThread(() -> Toast.makeText(bookDetailActivity.this,msg,Toast.LENGTH_LONG).show());
    }
    public void refreshCommentData(){
        HttpUtil.getRequestAsyn("/app/book/"+ BookID, new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mtoast("请求异常，加载不出来");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    /*获取所有书籍信息*/
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");

                    /*取出书籍评论*/
                    JSONObject book_comments=data.getJSONObject("comments");
                    int comment_size=book_comments.getInt("size");
                    JSONArray comments_list=book_comments.getJSONArray("data");
                    if(comment_size==0)
                    {
                        comment_list=null;
                    }
                    if(comment_size!=0){
                        for(int i = 0; i < comment_size; i++){
                            JSONObject comment_item = comments_list.getJSONObject(i);

                            User user = new User("小美","20230522@ruc.edu.cn", "12345678");
                            //user.setUserName("用户1");
                            user.setAvaID(String.valueOf(R.drawable.juicy_orange_smile_icon));

                            PieceComments comment = new PieceComments(comment_item.getString("content"),comment_item.getInt("likes"),user);
                            comment_list.add(comment);
                        }
                        runOnUiThread(() -> {
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.book_comment);
                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                        });
                        /*new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0;i < comment_list.size(); ++i){
                                    //                                Bitmap pic = HttpUtil.getAvaSyn(works.get(i).getUser().getAvaID());
                                    Bitmap pic = null;
                                    try {
                                        pic = Tools.getImageBitmapSyn(allCommentActivity.this,comment_list.get(i).getUser().getAvaID());
                                    } catch (IOException | JSONException | ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    comment_list.get(i).getUser().setAvator(pic);
                                    Log.d("bookcommentadpter","需要更新");
                                }

                            }
                        }).run();*/

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
