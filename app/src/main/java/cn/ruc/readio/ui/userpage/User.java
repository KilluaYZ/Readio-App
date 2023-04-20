package cn.ruc.readio.ui.userpage;

import android.graphics.Bitmap;

public class User {
    private String userName;
    private String eMail;
    private String phoneNumber;
    private Bitmap avator;

    public User(String userName,String eMail, String phoneNumber){
        this.userName = userName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public Bitmap getAvator() {
        return avator;
    }

    public void setAvator(Bitmap avator) {
        this.avator = avator;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
