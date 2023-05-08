package cn.ruc.readio.worksManageFragment;

import androidx.recyclerview.widget.RecyclerView;

import cn.ruc.readio.ui.works.Works;
import cn.ruc.readio.worksActivity.readWorksActivity;
import cn.ruc.readio.worksManageFragment.publishedManageFragment;
import cn.ruc.readio.worksManageFragment.draftManageFragment;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ruc.readio.R;
import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.List;

public class worksManageAdapter extends RecyclerView.Adapter<worksManageAdapter.ViewHolder>{
    private List<Works> WorksList;

    public worksManageAdapter(Context context, List<Works> WorksList){
        this.WorksList = WorksList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView workTitle;
        private TextView workContent;
        private TextView likesNum;
        private TextView collectsNum;
        private TextView commentsNum;
        private TextView seriesName;
        private TextView publishedTime;
        private TextView workTag;

        public ViewHolder(View view){
            super(view);
            workTitle = view.findViewById(R.id.manageWorkTitle);
//            workContent = view.findViewById(R.id.manageWorkContent);
            workTag = view.findViewById(R.id.workTag);
            seriesName = view.findViewById(R.id.manageSeriesTitle);
            likesNum = view.findViewById(R.id.manageLikeNumText);
            collectsNum = view.findViewById(R.id.manageCollectNumText);
            commentsNum = view.findViewById(R.id.manageCommentText);
            publishedTime = view.findViewById(R.id.managePublishedTime);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_works_manage,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Works works = WorksList.get(position);
        holder.workTitle.setText(works.getPieceTitle());
//        holder.workContent.setText(works.getContent());
        holder.seriesName.setText(works.getSerialTitle());
        holder.publishedTime.setText(works.getPublishedTime());
        holder.likesNum.setText(toString().valueOf(works.getLikesNum()));
        holder.collectsNum.setText(toString().valueOf(works.getCollectsNum()));
        holder.collectsNum.setText(toString().valueOf(works.getCollectsNum()));
    }

    @Override
    public int getItemCount() {
        return WorksList.size();
    }
}
