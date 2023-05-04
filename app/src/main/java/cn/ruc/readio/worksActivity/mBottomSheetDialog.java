package cn.ruc.readio.worksActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.ruc.readio.R;

public class mBottomSheetDialog extends BottomSheetDialog {

    public mBottomSheetDialog(@NonNull Context context, int theme) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        ImageView likePieceComment_button = (ImageView) findViewById(R.id.likePieceCommentButton);
//        likePieceComment_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                likePieceComment_button.setImageResource(R.drawable.likedcomment);
//            }
//        });
    }
}
