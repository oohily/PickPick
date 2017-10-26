package com.sandemarine.hsddialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zhongsz on 2017/8/9.
 */

public class DialogListAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater;
    private HSDDialogListListener listener;

    public DialogListAdapter(Context a, List<String> list, HSDDialogListListener listener) {
        this.list = list;
        this.listener = listener;
        inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null){
            v = inflater.inflate(R.layout.dialog_list_item,null);
        }
        TextView text = (TextView) v.findViewById(R.id.dialog_list_item_text);
        text.setText(list.get(i));
        if (listener != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(i);
                }
            });
        }
        return v;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }
}
