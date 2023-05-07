package cn.ruc.readio.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Auth {

    class Token{

        private final String storeName = "token";

        private String token = null;

        private Activity activity = null;

        public Token(Activity activity, String token){
            this.activity = activity;
            this.token = token;
        }

        public String getToken() {
            read();
            return token;
        }

        public void setToken(String token) {
            this.token = token;
            write();
        }

        public void write(){
            SharedPreferences sharedPreferences = activity.getSharedPreferences(storeName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", token);
            editor.commit();
        }

        public void read(){
            SharedPreferences sharedPreferences = activity.getSharedPreferences(storeName, Context.MODE_PRIVATE);
            token = sharedPreferences.getString("token","");
        }

        public void clear(){
            SharedPreferences sharedPreferences = activity.getSharedPreferences(storeName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

    static public void login(String phoneNumber, String passWord){

    }

    static public void register(String phoneNumber, String passWord){

    }

    static public void logout(){

    }


}
