package com.oohily.com.pickpick.MainView.presenter;

/**
 * Created by Zhongsz on 2017/7/24.
 */

public interface IMainPresenter {
    void loadData(String listname);
    void loadList();
    void addData(String name);
    void deleteData(int position);
    void deleteList(int position);
    void doLogOut();
    void addList(String listname);
    void changeList(int position);
    void doPick(long seed);
    void getWeather();
}
