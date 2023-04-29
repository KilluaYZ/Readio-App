package cn.ruc.readio.worksActivity;

import java.util.Date;

import cn.ruc.readio.ui.userpage.User;

public class PieceComments {
    private int commentID;
    private String content;
    private User user;
    private Date date;
    private int likesNum;

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

    public String getUser(){
        return user.getUserName();
    }

    public void setCommentID(int id){
        this.commentID = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
