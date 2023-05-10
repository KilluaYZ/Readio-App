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
import android.widget.Toast;

import cn.ruc.readio.R;
import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.List;

public class draftManageAdapter extends RecyclerView.Adapter<draftManageAdapter.ViewHolder>{
    private List<Works> WorksList;
    public draftManageAdapter(Context context, List<Works> WorksList){
        this.WorksList = WorksList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView draftTitle;
        private TextView draftSeriesName;
        private TextView startTime;
        private TextView publishButton;

        public ViewHolder(View view){
            super(view);
            draftTitle = view.findViewById(R.id.manageDraftTitle);
//            workContent = view.findViewById(R.id.manageWorkContent);
            publishButton = view.findViewById(R.id.publishDraftButton);
            draftSeriesName = view.findViewById(R.id.manageDraftSeriesTitle);
            startTime = view.findViewById(R.id.startDraftTime);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draft_manage,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(), "作品已成功发布！", Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Works works = WorksList.get(position);
        holder.draftTitle.setText(works.getPieceTitle());
//        holder.workContent.setText(works.getContent());
        holder.draftSeriesName.setText("系列名：" + works.getSerialTitle());
        holder.startTime.setText("创建时间："+works.getPublishedTime());
    }

    @Override
    public int getItemCount() {
        return WorksList.size();
    }
}
