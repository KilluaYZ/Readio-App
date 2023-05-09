package cn.ruc.readio.ui.works;
import android.util.Log;

import java.util.Date;

import cn.ruc.readio.ui.userpage.User;

public class Works {
    private int workID;
    private String serialTitle;
    private String pieceTitle;
    private String content;
    private User user;
    private Date date;
    private int likesNum;
    private tags tag;
    private int mylike;
    private int collectsNum;
    private int commentsNum;
    private String publishedTime;

    public Works(String content, String serialTitle, String pieceTitle, User user) {
        this.content = content;
        this.pieceTitle = pieceTitle;
        this.serialTitle = serialTitle;
        this.user = user;
        this.mylike = 0;
    }

    public Works(){
        mylike = 0;
    }
    public String getSerialTitle(){
        return serialTitle;
    }
    public String getPieceTitle(){
        return pieceTitle;
    }
    public String getContent(){
        return content;
    }
    public int getLikesNum(){
        return likesNum;
    }

    public int getWorkID(){ return workID;}
    public tags getTag(){ return tag;}
    public String getWorkUser(){
        return user.getUserName();
    }
    public String getAvaId(){return user.getAvaID();}

    public User getUser() {return user;}

    public int getMylike(){return mylike;}
    public String getPublishedTime(){return publishedTime;}
    public int getCollectsNum(){return collectsNum;}
    public int getCommentsNum(){return commentsNum;}
    public void setWorkID(int id){
        this.workID = id;
    }
    public void setSerialTitle(String serialTitle) {
        this.serialTitle = serialTitle;
    }

    public void setPieceTitle(String pieceTitle) {
        this.pieceTitle = pieceTitle;
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
    public void setTag(tags tag){
        this.tag = tag;
    }
    public void setPublishedTime(String publishedTime){ this.publishedTime = publishedTime; }
    public void setCollectsNum(int collectsNum){this.collectsNum = collectsNum;}
    public void setCommentsNum(int commentsNum){this.commentsNum = commentsNum;}
    public void changeMyLike(){
        if (mylike==0){
            mylike = 1;
        }
        else{
            mylike = 0;
        }
    }
    public void addLike(){
        Log.d("lalala", "+1");
        likesNum++;
    }
    public void subLike(){
        likesNum--;
    }
}

