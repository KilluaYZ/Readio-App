package cn.ruc.readio.ui.works;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.MainActivity;
import cn.ruc.readio.R;
import cn.ruc.readio.worksActivity.readWorksActivity;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder>{
    private List<Works> WorksList;

    public WorkAdapter(Context context, List<Works> WorksList){
        this.WorksList = WorksList;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView workTitle;
        private TextView workContent;
        private TextView workUser;
        private TextView likesNum;
        private TextView workTag;
        private ImageView likeButton;
        private ImageView userAva;
        public ViewHolder(View view){
            super(view);
            workTitle = view.findViewById(R.id.workTitle);
            workContent = view.findViewById(R.id.workContent);
            workUser = view.findViewById(R.id.workUser);
            likesNum = view.findViewById(R.id.likesNum);
            workTag = view.findViewById(R.id.workTag);
            likeButton = view.findViewById(R.id.likePieceButton);
            userAva = view.findViewById(R.id.userAvator);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.workContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                // 实际上点进每一个item都要传输作品的id
                Intent intent = new Intent(view.getContext(), readWorksActivity.class);
                intent.putExtra("extra_data",String.valueOf(WorksList.get(position).getWorkID()));
                Log.d("pieceid",String.valueOf(WorksList.get(position).getWorkID()));
                startActivity(view.getContext(), intent,null);
            }
        });

        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if(WorksList.get(position).getMylike()==0){
                    viewHolder.likeButton.setImageResource(R.drawable.msaik_like);
                    WorksList.get(position).addLike();
                    if(WorksList.get(position).getLikesNum() >= 1000){
                        String num = String.valueOf(WorksList.get(position).getLikesNum());
                        String num1 = num.substring(0,1);
                        String num2 = num.substring(1,2);
                        viewHolder.likesNum.setText(num1+"."+num2+"k");
                    }
                    else{viewHolder.likesNum.setText(String.valueOf(WorksList.get(position).getLikesNum()));}   //基于实际情况，点赞过万的情况就不写了
                }
                else{
                    viewHolder.likeButton.setImageResource(R.drawable.heart_plus_48);
                    WorksList.get(position).subLike();
                    if(WorksList.get(position).getLikesNum() >= 1000){
                        String num = String.valueOf(WorksList.get(position).getLikesNum());
                        String num1 = num.substring(0,1);
                        String num2 = num.substring(1,2);
                        viewHolder.likesNum.setText(num1+"."+num2+"k");
                    }
                    else{viewHolder.likesNum.setText(String.valueOf(WorksList.get(position).getLikesNum()));}   //基于实际情况，点赞过万的情况就不写了
                }
                WorksList.get(position).changeMyLike();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Works works = WorksList.get(position);
        holder.workTitle.setText(works.getPieceTitle());
        holder.workContent.setText(works.getContent());
        holder.workUser.setText(works.getWorkUser());
        if(works.getLikesNum() >= 1000){
            String num = String.valueOf(works.getLikesNum());
            String num1 = num.substring(0,1);
            String num2 = num.substring(1,2);
            holder.likesNum.setText(num1+"."+num2+"k");
        }
        else{holder.likesNum.setText(String.valueOf(works.getLikesNum()));}   //基于实际情况，点赞过万的情况就不写了
        if(works.getTag() == null){
            holder.workTag.setText("");
            holder.workTag.setVisibility(View.GONE);}
        else{holder.workTag.setText("#"+works.getTag().getContent());}
        Log.d("workadapter", "userAvator is null? = "+String.valueOf(works.getUser().getAvator()));
        holder.userAva.setImageBitmap(works.getUser().getAvator());
    }
    @Override
    public int getItemCount() {
        return WorksList.size();
    }
}
