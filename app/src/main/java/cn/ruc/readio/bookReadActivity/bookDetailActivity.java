package cn.ruc.readio.bookReadActivity;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
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
import android.widget.ImageView;
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
    private Activity bookDetailAct = this;
    private String BookName,Author;
    private int BookID;
    private List<PieceComments> comment_list=new ArrayList<>();
    private TextView book_name, author, book_abstract,view_count,length,likes,shares,like_this_book_text,add_shelf_text;
    private RelativeLayout detail_page;
    private ImageButton like_this_book,read_book,add_shelf;
    private ImageView book_cover;
    private View view;
    private RecyclerView recycler_view;
    private int like_book_times=0,add_shelf_times=0,if_like=0,if_add_bookmark=0,if_empty=0;
    private TextView no_comment;


    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookdetail);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }  //用于调整状态栏为透明色
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

        /*设置加入书架按钮*/
        add_shelf.setOnClickListener(view1 -> {
            add_shelf_times++;
            if((if_add_bookmark+add_shelf_times)%2==0){
                add_shelf.setImageResource(R.drawable.bookmark);
                add_shelf_text.setText("加入书架");
            }
            else {
                add_shelf.setImageResource(R.drawable.bookmarked);
                add_shelf_text.setText("已加入书架");
            }

        });

        /*设置阅读按钮*/
        read_book.setOnClickListener(view -> {
            Intent read_intent=new Intent(bookDetailActivity.this,readBookActivity.class);
            read_intent.putExtra("BookName",BookName);
            read_intent.putExtra("Author",Author);
            read_intent.putExtra("BookID",BookID);
            startActivity(read_intent);
        });

        /*设置点赞按钮*/
        like_this_book.setOnClickListener(view1 -> {
            like_book_times++;
            if((if_like+like_book_times)%2==0){
                like_this_book.setImageResource(R.drawable.thumb_up_1);
                like_this_book_text.setText("点赞");
            }
            else {
                like_this_book.setImageResource(R.drawable.thumb_up_ed);
                like_this_book_text.setText("已点赞");
            }

        });

        /*设置评论部分*/
        /*添加下划线*/
        TextView tv=findViewById(R.id.more_comment);
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        /*添加跳转到所有评论界面链接*/
        tv.setOnClickListener(view -> {
            if(if_empty==1)
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
        like_this_book=findViewById(R.id.like_this_book);
        read_book=findViewById(R.id.read);
        add_shelf=findViewById(R.id.add_bookmark);
        likes=findViewById(R.id.likes);
        shares=findViewById(R.id.shares);
        like_this_book_text=findViewById(R.id.like_this_book_text);
        add_shelf_text=findViewById(R.id.add_bookmark_text);
        book_cover=findViewById(R.id.picture);
        no_comment=findViewById(R.id.no_comment);

        book_name.setText(BookName);
        author.setText(Author);
        book_abstract.setText("这里是书籍简介~\n但这本书暂时没有~");
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
                    String added = data.getString("added");
                    if (added.equals("true")) {
                        if_add_bookmark = 1;
                        bookDetailAct.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                add_shelf.setImageResource(R.drawable.bookmarked);
                                add_shelf_text.setText("已加入书架");
                            }
                        });
                    } else {
                        bookDetailAct.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                add_shelf.setImageResource(R.drawable.bookmark);
                                add_shelf_text.setText("加入书架");
                            }
                        });
                    }
                    /*取出是否点赞*/
                    String liked = data.getString("liked");
                    if (liked.equals("true")) {
                        if_like = 1;
                        bookDetailAct.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                like_this_book.setImageResource(R.drawable.thumb_up_ed);
                                like_this_book_text.setText("已点赞");
                            }
                        });
                    } else {
                        bookDetailAct.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                like_this_book.setImageResource(R.drawable.thumb_up_1);
                                like_this_book_text.setText("点赞");
                            }
                        });
                    }
                    /*取出书籍自身信息部分*/
                    JSONObject book_info = data.getJSONObject("book_info");
                    bookDetailAct.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                book_name.setText(book_info.getString("bookName"));
                            } catch (JSONException e) {
                                Tools.my_toast(bookDetailAct,"加载失败");
                            }
                            try {
                                length.setText(book_info.getString("length"));
                            } catch (JSONException e) {
                                Tools.my_toast(bookDetailAct,"加载失败");
                            }
                            try {
                                view_count.setText(book_info.getString("views"));
                            } catch (JSONException e) {
                                Tools.my_toast(bookDetailAct,"加载失败");
                            }
                            try {
                                likes.setText(book_info.getString("likes"));
                            } catch (JSONException e) {
                                Tools.my_toast(bookDetailAct,"加载失败");
                            }
                            shares.setText(book_info.optString("shares", "0"));
                            try {
                                if (!book_info.getString("abstract").equals("null")) {
                                    book_abstract.setText(book_info.getString("abstract"));
                                }
                            } catch (JSONException e) {
                                Tools.my_toast(bookDetailAct,"加载失败");
                            }

                        }
                    });

                    if(!book_info.getString("bitmap").equals("null")) {
                        final Bitmap[] cover = {null};
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cover[0] = Tools.getImageBitmapSyn(bookDetailActivity.this, book_info.getString("bitmap"));
                                } catch (JSONException | IOException | ParseException e) {
                                    Tools.my_toast(bookDetailAct,"加载失败");
                                }
                            }
                        }).run();
                        bookDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                book_cover.setImageBitmap(cover[0]);
                            }
                        });
                    }


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
            @SuppressLint("NotifyDataSetChanged")
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
                        if_empty=1;
                        no_comment.setText("这本书暂时没有评论哦~\n来当第一位吧！");
                        no_comment.setGravity(Gravity.CENTER);
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
                            RecyclerView recyclerView = findViewById(R.id.book_comment);
                            if(recyclerView!=null){
                                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                            }
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
