package cn.ruc.readio.util;

import android.app.Activity;
import android.widget.Toast;

public class Tools {
    public static void my_toast(Activity activity, String text){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
