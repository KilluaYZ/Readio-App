package cn.ruc.readio.ui.shelf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.util.List;
import cn.ruc.readio.R;
import cn.ruc.readio.bookReadActivity.readBookActivity;

public class bookAdapter extends BaseAdapter {

    private final List<BookItem> bookList;
    private final Context context;
    public bookAdapter(Context context, List<BookItem> list)
    {
        this.context=context;
        this.bookList=list;
    }

    public int getCount()
    {
        if(bookList.isEmpty())
        {
            return 0;
        }
        return bookList.size();
    }
    public Object getItem(int position)
    {
        if(position>bookList.size()-1)
        {
            return null;
        }
        return bookList.get(position);
    }
    public long getItemId(int position)
    {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.book_title);
            holder.cover = convertView.findViewById(R.id.book_cover);
            holder.jumpview= convertView.findViewById(R.id.book_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BookItem books = bookList.get(position);
        if (books != null) {
            holder.text.setText(books.getBookName());
            if(books.getCover()==null)
            {
                holder.cover.setImageResource(R.drawable.default_cover2);
            }else{
            holder.cover.setImageBitmap(books.getCover());}

            /*设置跳转阅读界面*/
            holder.jumpview.setOnClickListener(view -> {
                Intent read_intent=new Intent();
                read_intent.setClass(context, readBookActivity.class);
                read_intent.putExtra("BookName",bookList.get(position).getBookName());
                read_intent.putExtra("Author",bookList.get(position).getAuthor());
                context.startActivity(read_intent);
            });

        }
        return convertView;
    }

    static class ViewHolder {
        TextView text;
        ImageView cover;
        CardView jumpview;
    }
}

