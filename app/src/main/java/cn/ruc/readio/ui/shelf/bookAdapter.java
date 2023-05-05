package cn.ruc.readio.ui.shelf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ruc.readio.R;

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
        /*convertView = bookInflater.inflate(R.layout.book_item,null);
        TextView book_title = (TextView)convertView.findViewById(R.id.book_title);
        ImageView iv =   (ImageView)convertView.findViewById(R.id.book_cover);
        BookItem value = (BookItem) bookList.get(position);
        if(null != value)
        {
            book_title.setText(value.getTitle());
            //iv.setImageBitmap(value.getCover());
            iv.setImageResource(value.getPic());
        }
        return convertView;*/

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.book_title);
            holder.cover = (ImageView) convertView.findViewById(R.id.book_cover);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BookItem books = bookList.get(position);
        if (books != null) {
            holder.text.setText(books.getTitle());
            holder.cover.setImageResource(books.getPic());
        }
        return convertView;
    }

    class ViewHolder {
        TextView text;
        ImageView cover;
    }
}

