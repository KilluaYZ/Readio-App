package cn.ruc.readio.ui.commend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.ruc.readio.R;


public class recyclerViewadapter extends RecyclerView.Adapter {
    private final List<DataBean> lists;
    private final Context context;

    public recyclerViewadapter(List<DataBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }


    static class myholder extends RecyclerView.ViewHolder{

        private final TextView tv1;
        private final TextView tv2;
        public myholder(View itemView) {
            super(itemView);
            tv1= (TextView) itemView.findViewById(R.id.quote);
            tv2= (TextView) itemView.findViewById(R.id.source);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: "+lists.get(position).getAutor());
        ((myholder)holder).tv1.setText(lists.get(position).getAutor());
        ((myholder)holder).tv2.setText(lists.get(position).getSource());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
