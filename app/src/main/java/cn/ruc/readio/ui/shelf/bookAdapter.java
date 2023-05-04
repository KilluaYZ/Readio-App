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
    private final LayoutInflater bookInflater;

    private final List<BookItem> bookList = new ArrayList<>();
    public bookAdapter(Context context, List<BookItem> list)
    {
        bookInflater = LayoutInflater.from(context);
        bookList.addAll(list);
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
        convertView = bookInflater.inflate(R.layout.book_item,null);
        TextView book_title = (TextView)convertView.findViewById(R.id.book_title);
        ImageView iv =   (ImageView)convertView.findViewById(R.id.book_cover);
        BookItem value = (BookItem) bookList.get(position);
        if(null != value)
        {
            book_title.setText(value.getTitle());
            iv.setImageBitmap(value.getCover());
        }
        return convertView;
    }

}
