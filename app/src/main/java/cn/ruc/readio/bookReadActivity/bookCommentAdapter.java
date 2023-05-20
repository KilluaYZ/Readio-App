package cn.ruc.readio.bookReadActivity;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.ruc.readio.R;
import cn.ruc.readio.ui.works.WorkAdapter;
import cn.ruc.readio.ui.works.Works;
import cn.ruc.readio.worksActivity.PieceComments;

public class bookCommentAdapter extends RecyclerView.Adapter<bookCommentAdapter.ViewHolder> {
    private List<PieceComments> CommentsList;
    private int like_comment_times = 0;
    private final Context context;

    public bookCommentAdapter(Context context, List<PieceComments> CommentsList){
        this.CommentsList = CommentsList;
        this.context = context;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView commentContent;
        private TextView commentUser;
        private TextView likesNum;
        private CardView jumpView;
        public ViewHolder(View view){
            super(view);
            commentContent = view.findViewById(R.id.commentContentText);
            commentUser = view.findViewById(R.id.commentUserText);
            likesNum = view.findViewById(R.id.likePieceCommentNum);
            jumpView=view.findViewById(R.id.comment_card);
        }
    }

    @NonNull
    @Override
    public bookCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_comment_item,parent,false);
        bookCommentAdapter.ViewHolder viewHolder = new bookCommentAdapter.ViewHolder(view);
        ImageView likePieceComment_button = view.findViewById(R.id.likePieceCommentButton);
        likePieceComment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                like_comment_times++;
//                if(like_comment_times%2==0){
//                likePieceComment_button.setImageResource(R.drawable.likecomment);}
//                else {
//                    likePieceComment_button.setImageResource(R.drawable.likedcomment);
//                }
                likePieceComment_button.setImageResource(R.drawable.likecomment);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull bookCommentAdapter.ViewHolder holder, int position) {
        PieceComments comment = CommentsList.get(position);
        holder.commentContent.setText(comment.getContent());
        holder.commentUser.setText(comment.getUser());
        holder.likesNum.setText(String.valueOf(comment.getLikesNum()));
        holder.jumpView.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setClass(context, commentDetailActivity.class);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return CommentsList.size();
    }
}
