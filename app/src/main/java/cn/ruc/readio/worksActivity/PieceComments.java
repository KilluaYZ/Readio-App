package cn.ruc.readio.worksActivity;

import android.graphics.Bitmap;

import java.util.Date;

import cn.ruc.readio.ui.userpage.User;

public class PieceComments {
    private int commentID;
    private String content;
    private User user;
    private String date;
    private int likesNum;
    private int childCommentNum;

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
    public String getUserName(){
        return user.getUserName();
    }
    public Bitmap getUserAvator(){return user.getAvator();}
    public User getUser(){
        return user;
    }
    public String getDate(){return date;};

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

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
