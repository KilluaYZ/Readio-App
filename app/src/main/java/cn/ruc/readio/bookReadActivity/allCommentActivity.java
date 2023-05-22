package cn.ruc.readio.bookReadActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.ruc.readio.R;
import cn.ruc.readio.ui.commend.commendCardAdapter;
import cn.ruc.readio.ui.userpage.User;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import cn.ruc.readio.worksActivity.PieceComments;
import cn.ruc.readio.worksActivity.pieceCommentAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class allCommentActivity extends AppCompatActivity {

    private List<PieceComments> comment_list =new ArrayList<>();
    private String BookName,Author;
    private int BookID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comment);

        /*接受传递的消息*/
        Intent intent = getIntent();

        /*获取内容*/
        BookName = intent.getStringExtra("BookName");
        Author = intent.getStringExtra("Author");
        BookID = intent.getIntExtra("BookID",0);

        refreshData();

        if(comment_list==null) initdata();

        LinearLayoutManager m=new LinearLayoutManager(allCommentActivity.this);
        RecyclerView recycler_view=findViewById(R.id.comments_recyclerView);
        recycler_view.setLayoutManager(m);
        aBookCommentAdapter myAdapter = new aBookCommentAdapter(allCommentActivity.this,comment_list);
        recycler_view.setAdapter(myAdapter);


    }

    public void initdata(){
        User user = new User("小美","20230522@ruc.edu.cn", "12345678");
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("好看好看好看好看", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));
        comment_list.add(new PieceComments("喜欢喜欢喜欢", 12, user));


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
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comments_recyclerView);
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

    private void mtoast(String msg){
        runOnUiThread(() -> Toast.makeText(allCommentActivity.this,msg,Toast.LENGTH_LONG).show());
    }

}