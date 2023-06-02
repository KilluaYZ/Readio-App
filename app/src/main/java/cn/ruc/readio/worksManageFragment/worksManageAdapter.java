package cn.ruc.readio.worksManageFragment;

import androidx.recyclerview.widget.RecyclerView;

import cn.ruc.readio.ui.works.Works;
import cn.ruc.readio.userPageActivity.ReEditActivity;
import cn.ruc.readio.userPageActivity.editWorkActivity;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import cn.ruc.readio.worksActivity.readWorksActivity;
import cn.ruc.readio.worksManageFragment.publishedManageFragment;
import cn.ruc.readio.worksManageFragment.draftManageFragment;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.ruc.readio.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class worksManageAdapter extends RecyclerView.Adapter<worksManageAdapter.ViewHolder>{
    private List<Works> WorksList;
    private static worksManageAdapter mAdapter;
    private publishedManageFragment fragment;
    public worksManageAdapter(publishedManageFragment fragment, List<Works> WorksList){
        mAdapter = this;
        this.WorksList = WorksList;
        this.fragment = fragment;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView workTitle;
        private TextView workContent;
        private TextView likesNum;
        private TextView collectsNum;
        private TextView commentsNum;
        private TextView seriesName;
        private TextView publishedTime;
        private TextView withdrawButton;
        private TextView deleteButton;
        private TextView editButton;

        public ViewHolder(View view){
            super(view);
            workTitle = view.findViewById(R.id.manageWorkTitle);
//            workContent = view.findViewById(R.id.manageWorkContent);
            withdrawButton = view.findViewById(R.id.withdrawWorkButton);
            seriesName = view.findViewById(R.id.manageSeriesTitle);
            likesNum = view.findViewById(R.id.manageLikeNumText);
            collectsNum = view.findViewById(R.id.manageCollectNumText);
            commentsNum = view.findViewById(R.id.manageCommentText);
            publishedTime = view.findViewById(R.id.managePublishedTime);
            deleteButton = view.findViewById(R.id.deleteWorkButton);
            editButton = view.findViewById(R.id.editWorkButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_works_manage,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                // 实际上点进每一个item都要传输作品的id
                Intent intent = new Intent(view.getContext(), ReEditActivity.class);
                intent.putExtra("piecesId", String.valueOf(WorksList.get(position).getWorkID()));
                intent.putExtra("piecesContent", String.valueOf(WorksList.get(position).getContent()));
                Log.d("pieceid", String.valueOf(WorksList.get(position).getWorkID()));
                startActivity(view.getContext(), intent, null);
            }
        });
        viewHolder.withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Works worki = WorksList.get(position);
                ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
                queryParam.add(new Pair<>("piecesId",toString().valueOf(worki.getWorkID())));
                HttpUtil.getRequestWithTokenAsyn(publishedManageFragment.worksManageFrag.getActivity(), "/works/changePiecesStatus", queryParam, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Tools.my_toast(publishedManageFragment.worksManageFrag.getActivity(),"撤回作品失败，请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Tools.my_toast(publishedManageFragment.worksManageFrag.getActivity(),"删除作品成功！");
//                            mAdapter.notifyDataSetChanged();
                        fragment.refreshData();
                    }
                });
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Works worki = WorksList.get(position);
                ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
                queryParam.add(new Pair<>("piecesId",toString().valueOf(worki.getWorkID())));

                    HttpUtil.getRequestWithTokenAsyn(publishedManageFragment.worksManageFrag.getActivity(), "/works/delPieces", queryParam, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Tools.my_toast(publishedManageFragment.worksManageFrag.getActivity(),"删除作品失败，请检查网络");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Tools.my_toast(publishedManageFragment.worksManageFrag.getActivity(),"删除作品成功！");
//                            mAdapter.notifyDataSetChanged();
                            fragment.refreshData();
                        }
                    });


            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Works works = WorksList.get(position);
        holder.workTitle.setText(works.getPieceTitle());
//        holder.workContent.setText(works.getContent());
        holder.seriesName.setText("系列名：" +works.getSerialTitle());
        holder.publishedTime.setText("发布时间：" + works.getPublishedTime());
        holder.likesNum.setText(toString().valueOf(works.getLikesNum()));
        holder.collectsNum.setText(toString().valueOf(works.getCollectsNum()));
        holder.collectsNum.setText(toString().valueOf(works.getCollectsNum()));
    }

    @Override
    public int getItemCount() {
        return WorksList.size();
    }
}
