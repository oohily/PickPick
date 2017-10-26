package com.oohily.com.pickpick.MainView.model;

/**
 * Created by Zhongsz on 2017/7/25.
 */

public class ListItem {
    private String listname;
    private boolean status;

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ListItem(String listname, boolean status) {

        this.listname = listname;
        this.status = status;
    }
}
