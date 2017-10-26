package com.oohily.com.pickpick.Login.model;

/**
 * Created by Zhongsz on 2017/7/17.
 */

public interface IUser {
    String getName();

    String getPasswd();

    boolean checkUserValidity();
}
