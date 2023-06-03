package cn.ruc.readio.worksActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cn.ruc.readio.ui.userpage.User;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PieceComments {
    private int commentID;
    private String bookId;
    private String content;
    private User user;
    private String date;
    private int likesNum;
    private int childCommentNum;
    private String if_liked;

    public PieceComments(String content, int LikesNum, User user) {
        this.content = content;
        this.likesNum = LikesNum;
        this.user = user;
    }

    public PieceComments(){

    }
    public int getCommentID(){
        return commentID;
    }
    public String getContent(){
        return content;
    }
    public int getLikesNum(){
        return likesNum;
    }
    public int getChildCommentNum(){
        return childCommentNum;
    }
    public String getUserName(){return user.getUserName();}
    public Bitmap getUserAvator(){return user.getAvator();}
    public User getUser(){return user;}
    public String getDate(){return date;}
    public String getIf_liked(){return if_liked;}
    public String getBookId(){return bookId;}

    public void setCommentID(int id){
        this.commentID = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }
    public void setChildCommentNum(int childCommentNum) {
        this.childCommentNum = childCommentNum;
    }
    public void setIf_liked(String if_liked){this.if_liked=if_liked;}

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void bookcomment_like(Activity act, int like) throws JSONException {
        if(like==1){
            likesNum++;
        }else{
            likesNum--;
        }
        JSONObject json = new JSONObject();
        json.put("bookId",bookId);
        json.put("commentId",String.valueOf(commentID));
        json.put("like",like);
        HttpUtil.postRequestWithTokenJsonAsyn(act, "/app/book/"+bookId+"/comments/"+commentID+"/update", json.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.my_toast(act,"点赞失败，请检查网络");
                //Toast.makeText(context,"点赞失败，请检查网络",Toast.LENGTH_SHORT);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Tools.my_toast(act,"点赞成功！");
            }
        });
    }
}
