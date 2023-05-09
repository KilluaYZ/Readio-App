package cn.ruc.readio.worksActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.ruc.readio.databinding.ActivityReadWorksBinding;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentUserpageBinding;
import cn.ruc.readio.ui.userpage.User;
import cn.ruc.readio.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class readWorksActivity extends AppCompatActivity {
    ImageView ava = null;
    int like_clicked_times = 0;
    int collect_clicked_times = 0;
    public List<PieceComments> comment_list = new ArrayList<>();
    private mBottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;
    private ActivityReadWorksBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityReadWorksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String workId;
        Intent intent = getIntent();
        workId = intent.getStringExtra("extra_data");
        TextView follow_button = (TextView) findViewById(R.id.followAuthorButton);
        EditText writeComment_button = (EditText) findViewById(R.id.writeComment);
        ImageView sendComment_button = (ImageView) findViewById(R.id.sendPieceCommentButton);
        ImageView like_button = (ImageView) findViewById(R.id.likePieceButton);
        ImageView collect_button = (ImageView) findViewById(R.id.collectPieceButton);
        ImageView read_comment_button = (ImageView) findViewById(R.id.commentZoneButton);
        TextView read_content = (TextView) findViewById(R.id.readWorkContentText);
        TextView read_title = (TextView) findViewById(R.id.workTitleText);
        TextView readSerialName = (TextView) findViewById(R.id.readSerialText);
        TextView userName = (TextView) findViewById(R.id.readUserNameText);
        ImageView exitRead_button = (ImageView) findViewById(R.id.exitRead);

            ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
            queryParam.add(new Pair<>("piecesId",workId));
            Log.d("piecesId",workId);
            HttpUtil.getRequestAsyn("/works/getPiecesDetail", queryParam, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mtoast("请求异常，加载不出来");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.code() == 200){
                            String s = response.body().string();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject data = jsonObject.getJSONObject("data");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        read_content.setText("\n"+data.getString("content"));
                                        read_title.setText("\n"+data.getString("title"));
                                        readSerialName.setText("合集："+data.getJSONObject("series").getString("seriesName")+" ");
                                        userName.setText((data.getJSONObject("user").getString("userName")));
                                        String avaId = data.getJSONObject("user").getString(("avator"));
                                        HttpUtil.getAvaAsyn(avaId,binding.authorAvator,readWorksActivity.this);
//                                        ava.setImageBitmap(HttpUtil.getAvaSyn(avaId));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else{
                            mtoast("请求出错");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

         exitRead_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
        /*
        判断用户是否已关注太太，如果已关注，follow_button要setGone
         */
        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(readWorksActivity.this, "成功关注太太", Toast.LENGTH_SHORT).show();
                /*
                传至服务器，更新数据库
                 */
                follow_button.setVisibility(view.GONE);
            }
        });

        sendComment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(writeComment_button.getText())){
                    Toast.makeText(readWorksActivity.this, "还没有编写评论哦", Toast.LENGTH_SHORT).show();
                }else{
                    /*
                    发送评论给数据库
                     */
                    Toast.makeText(readWorksActivity.this, "已发送~", Toast.LENGTH_SHORT).show();
                    writeComment_button.setText("");
                    /*
                    刷新评论区页面？
                     */
                }
            }
        });

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like_clicked_times++;
                if(like_clicked_times % 2 == 0){
                    like_button.setImageResource(R.drawable.like);}
                else{
                    like_button.setImageResource(R.drawable.liked);
                }
            }
        });

        collect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_clicked_times++;
                if(collect_clicked_times % 2 == 0){
                    collect_button.setImageResource(R.drawable.collect);}
                else{
                    collect_button.setImageResource(R.drawable.collected);
                }
            }
        });

        read_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 10; i++ ){
                    User user = new User("呆头鹅","2020201694@ruc.edu.cn","123456");
                PieceComments commenti = new PieceComments("好棒",12,user);
                comment_list.add(commenti);
                }
                bottomsheet();
            }
        });
    }

    private void bottomsheet(){
            if (bottomSheetDialog == null) {
                //创建布局
                View view = LayoutInflater.from(this).inflate(R.layout.work_comment_bottomsheet, null, false);
                bottomSheetDialog = new mBottomSheetDialog(this, R.style.work_comment_bottomsheet);
                RecyclerView recyclerView = view.findViewById(R.id.dialog_recycleView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                pieceCommentAdapter mainAdapter = new pieceCommentAdapter(bottomSheetDialog.getContext(), comment_list);
                recyclerView.setAdapter(mainAdapter);

                //设置点击dialog外部不消失
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                //核心代码 解决了无法去除遮罩问题
                bottomSheetDialog.getWindow().setDimAmount(0f);
                //设置布局
                bottomSheetDialog.setContentView(view);
                //用户行为
                bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
                //dialog的高度
                bottomSheetBehavior.setPeekHeight(getWindowHeight());
            }
            //展示
            bottomSheetDialog.show();

            //重新用户的滑动状态
                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View view, int newState) {
                    //监听BottomSheet状态的改变
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetDialog.dismiss();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }

                @Override
                public void onSlide(@NonNull View view, float v) {
                    //监听拖拽中的回调，根据slideOffset可以做一些动画
                }
            });

        }
    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return heightPixels - heightPixels / 4;
    }

    private void mtoast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(readWorksActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });
    }

}

