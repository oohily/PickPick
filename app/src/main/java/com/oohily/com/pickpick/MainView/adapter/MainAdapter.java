package com.oohily.com.pickpick.MainView.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oohily.com.pickpick.MainView.model.Item;
import com.oohily.com.pickpick.MainView.presenter.IMainPresenter;
import com.oohily.com.pickpick.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhongsz on 2017/7/24.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Item> itemList;
    IMainPresenter presenter;
    private static final String TAG = "MainAdapter";
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_main_recycler_name)
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public MainAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void register(IMainPresenter mainPresenter){
        this.presenter = mainPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                presenter.deleteData(position);
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
