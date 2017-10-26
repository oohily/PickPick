package com.oohily.com.pickpick.MainView.presenter;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.internal.LinkedTreeMap;
import com.oohily.com.pickpick.Api.ApiManager;
import com.oohily.com.pickpick.Entity.ListEntity;
import com.oohily.com.pickpick.Entity.PersonalEntity;
import com.oohily.com.pickpick.MainView.model.Item;
import com.oohily.com.pickpick.MainView.model.ListItem;
import com.oohily.com.pickpick.MainView.view.IMainView;
import com.oohily.com.pickpick.MyApplication;
import com.oohily.com.pickpick.configs.Constant;
import com.oohily.com.pickpick.configs.StringConstant;
import com.oohily.com.pickpick.gen.ListEntityDao;
import com.oohily.com.pickpick.gen.PersonalEntityDao;
import com.oohily.com.pickpick.models.HttpResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhongsz on 2017/7/24.
 */

public class MainPresenter implements IMainPresenter {
    private IMainView iMainView;
    private List<Item> itemList;
    private List<ListItem> listItemList;
    private String username;
    private String list;
    private SharedPreferences preferences;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    @Override
    public void loadData(String listname) {
        itemList = new ArrayList<>();
        preferences = MyApplication.getApplication().getSharedPreferences(StringConstant.USER_PREFERENCE, Context.MODE_PRIVATE);
        username = preferences.getString(StringConstant.USER_NAME, "");
        this.list = listname;
        //获取当前用户
        ListEntityDao listEntityDao = MyApplication.getApplication().getDaoSession().getListEntityDao();
        List<ListEntity> listEntities = listEntityDao.queryBuilder().where(ListEntityDao.Properties.Listname.eq(username + "_" + listname)).list();
        for (ListEntity listEntity : listEntities) {
            itemList.add(new Item(listEntity.getItemname(), listEntity.getRank(), listEntity.getRate()));
        }
        iMainView.onLoadData(itemList);
    }

    @Override
    public void addData(String name) {
        //添加list内容
        ListEntityDao listEntityDao = MyApplication.getApplication().getDaoSession().getListEntityDao();
        if (listEntityDao.queryBuilder().where(ListEntityDao.Properties.Itemname.eq(name),
                ListEntityDao.Properties.Listname.eq(username + "_" + list)).list().size() == 0) {
            itemList.add(new Item(name, 0, 0));
            listEntityDao.insert(new ListEntity(null, username + "_" + list, name, 0, 0));
        } else {
            iMainView.onAddData(Constant.FAIL, itemList.size() - 1);
        }
        iMainView.onAddData(Constant.SUCCESS, itemList.size() - 1);
    }

    @Override
    public void deleteData(int position) {
        Item mitem = itemList.get(position);
        ListEntityDao listEntityDao = MyApplication.getApplication().getDaoSession().getListEntityDao();
        ListEntity listEntity = listEntityDao.queryBuilder().where(ListEntityDao.Properties.Itemname.eq(mitem.getName()),
                ListEntityDao.Properties.Listname.eq(username + "_" + list)).unique();
        listEntityDao.delete(listEntity);
        itemList.remove(position);
        iMainView.onDeleteData(position);
    }

    @Override
    public void doLogOut() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        MyApplication.getApplication().setLogin(false);
        iMainView.verifyUser();
    }

    @Override
    public void loadList() {
        listItemList = new ArrayList<>();
        PersonalEntityDao personalEntityDao = MyApplication.getApplication().getDaoSession().getPersonalEntityDao();
        List<PersonalEntity> personalEntityList = personalEntityDao.loadAll();
        for (PersonalEntity personalEntity : personalEntityList) {
            listItemList.add(new ListItem(personalEntity.getLsitname().split("_")[1], false));
        }
        iMainView.onLoadList(listItemList);
    }

    @Override
    public void addList(String listname) {
        if (listname.equals("")) {
            iMainView.onAddList(Constant.FAIL, listItemList.size() - 1);
        } else {
            PersonalEntityDao personalEntityDao = MyApplication.getApplication().getDaoSession().getPersonalEntityDao();
            if (personalEntityDao.queryBuilder().where(PersonalEntityDao.Properties.Listname.eq(username + "_" + listname)).list().size() == 0) {
                listItemList.add(new ListItem(listname, false));
                personalEntityDao.insert(new PersonalEntity(null, username, username + "_" + listname));
            } else {
                iMainView.onAddList(Constant.FAIL, listItemList.size() - 1);
            }
            iMainView.onAddList(Constant.SUCCESS, listItemList.size() - 1);
        }
    }

    @Override
    public void changeList(int position) {
        list = listItemList.get(position).getListname();
        loadData(list);
        iMainView.dismissList();
    }

    @Override
    public void deleteList(int position) {
        ListItem mitem = listItemList.get(position);
        //删表
        PersonalEntityDao personalEntityDao = MyApplication.getApplication().getDaoSession().getPersonalEntityDao();
        PersonalEntity personalEntity = personalEntityDao.queryBuilder().where(PersonalEntityDao.Properties.Listname.eq(username + "_" + mitem.getListname())).unique();
        personalEntityDao.delete(personalEntity);
        //删表内容
        ListEntityDao listEntityDao = MyApplication.getApplication().getDaoSession().getListEntityDao();
        List<ListEntity> listEntities = listEntityDao.queryBuilder().where(ListEntityDao.Properties.Listname.eq(username + "_" + mitem.getListname())).list();
        for (ListEntity listEntity : listEntities) {
            listEntityDao.delete(listEntity);
        }
        listItemList.remove(position);
        iMainView.onDeleteList(position);
        loadData(listItemList.get(0).getListname());
        iMainView.dismissList();
    }

    @Override
    public void doPick(long seed) {
        if (itemList.size() == 0) {
            iMainView.onPickResult("吃空气吧");
        } else {
            Random randa = new Random(seed);
            Random random = new Random(randa.nextLong());
            int position = random.nextInt(itemList.size());
            iMainView.onPickResult(itemList.get(position).getName()); //
        }
    }

    @Override
    public void getWeather() {
        ApiManager.getInstance().getWeatherApi().getNowWeather("CN101280102","00936ee10786436cbe8e90ff8929616c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        iMainView.onGetWeather(((LinkedTreeMap)httpResult.getHeWeather5().get(0))); //
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
