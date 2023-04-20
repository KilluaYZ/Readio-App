package cn.ruc.readio.ui.works;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.ruc.readio.R;

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
        public ViewHolder(View view){
            super(view);
            workTitle = view.findViewById(R.id.workTitle);
            workContent = view.findViewById(R.id.workContent);
            workUser = view.findViewById(R.id.workUser);
            likesNum = view.findViewById(R.id.likesNum);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Works works = WorksList.get(position);
        holder.workTitle.setText(works.getPieceTitle());
        holder.workContent.setText(works.getContent());
        holder.workUser.setText(works.getWorkUser());
        holder.likesNum.setText(String.valueOf(works.getLikesNum()));

    }
    @Override
    public int getItemCount() {
        return WorksList.size();
    }
}
