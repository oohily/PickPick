package com.oohily.com.pickpick.MainView.view;

import com.google.gson.internal.LinkedTreeMap;
import com.oohily.com.pickpick.MainView.model.Item;
import com.oohily.com.pickpick.MainView.model.ListItem;

import java.util.List;

/**
 * Created by Zhongsz on 2017/7/24.
 */

public interface IMainView {
    void initView();
    void onLoadList(List<ListItem> listItemList);
    void verifyUser();
    void onLoadData(List<Item> itemList);
    void onAddData(int status,int position);
    void onAddList(int status,int position);
    void onDeleteData(int position);
    void onDeleteList(int position);
    void dismissList();
    void onPickResult(String result);
    void onGetWeather(LinkedTreeMap result);
}
