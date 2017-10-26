package com.oohily.com.pickpick.MainView.model;

/**
 * Created by Zhongsz on 2017/7/24.
 */

public class Item implements IItem{
    private String name;
    private int rank;
    private int rate;

    public Item(String name, int rank, int rate) {
        this.name = name;
        this.rank = rank;
        this.rate = rate;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String getName() {
        return name;
    }
}
