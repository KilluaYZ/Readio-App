package cn.ruc.readio.ui.works;
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

    public Works(String content, int LikesNum, String serialTitle, String pieceTitle, User user) {
        this.content = content;
        this.likesNum = LikesNum;
        this.pieceTitle = pieceTitle;
        this.serialTitle = serialTitle;
        this.user = user;
    }

    public Works(){

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

    public String getWorkUser(){
        return user.getUserName();
    }

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
}

