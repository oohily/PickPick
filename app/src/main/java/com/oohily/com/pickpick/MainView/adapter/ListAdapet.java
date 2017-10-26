package com.oohily.com.pickpick.MainView.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.oohily.com.pickpick.MainView.model.ListItem;
import com.oohily.com.pickpick.MainView.presenter.IMainPresenter;
import com.oohily.com.pickpick.MainView.presenter.MainPresenter;
import com.oohily.com.pickpick.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhongsz on 2017/7/25.
 */

public class ListAdapet extends RecyclerView.Adapter<ListAdapet.ListHolder> {
    private List<ListItem> listItems;
    IMainPresenter mainPresenter;

    static class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_main_list_name)
        TextView listname;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ListAdapet(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    public void register(IMainPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position) {
        holder.listname.setText(listItems.get(position).getListname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.changeList(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mainPresenter.deleteList(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
