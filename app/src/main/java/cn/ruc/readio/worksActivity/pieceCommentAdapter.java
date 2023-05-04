package cn.ruc.readio.worksActivity;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.ruc.readio.R;
import cn.ruc.readio.ui.works.WorkAdapter;
import cn.ruc.readio.ui.works.Works;

public class pieceCommentAdapter extends RecyclerView.Adapter<pieceCommentAdapter.ViewHolder> {
    private List<PieceComments> CommentsList;
    private int like_comment_times = 0;

    public pieceCommentAdapter(Context context, List<PieceComments> CommentsList){
        this.CommentsList = CommentsList;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView commentContent;
        private TextView commentUser;
        private TextView likesNum;
        public ViewHolder(View view){
            super(view);
            commentContent = view.findViewById(R.id.commentContentText);
            commentUser = view.findViewById(R.id.commentUserText);
            likesNum = view.findViewById(R.id.likePieceCommentNum);
        }
    }

    @NonNull
    @Override
    public pieceCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_piece_comment,parent,false);
        pieceCommentAdapter.ViewHolder viewHolder = new pieceCommentAdapter.ViewHolder(view);
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
    public void onBindViewHolder(@NonNull pieceCommentAdapter.ViewHolder holder, int position) {
        PieceComments comment = CommentsList.get(position);
        holder.commentContent.setText(comment.getContent());
        holder.commentUser.setText(comment.getUser());
        holder.likesNum.setText(String.valueOf(comment.getLikesNum()));
    }
    @Override
    public int getItemCount() {
        return CommentsList.size();
    }
}
