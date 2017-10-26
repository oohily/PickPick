package com.oohily.com.pickpick.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Zhongsz on 2017/7/24.
 */
@Entity
public class ListEntity {
    @Id
    private Long id;
    @NotNull
    private String listname;
    private String itemname;
    private int rank;
    private int rate;
    @Generated(hash = 1667682832)
    public ListEntity(Long id, @NotNull String listname, String itemname, int rank,
            int rate) {
        this.id = id;
        this.listname = listname;
        this.itemname = itemname;
        this.rank = rank;
        this.rate = rate;
    }
    @Generated(hash = 996695932)
    public ListEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getItemname() {
        return this.itemname;
    }
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
    public int getRank() {
        return this.rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRate() {
        return this.rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public String getListname() {
        return this.listname;
    }
    public void setListname(String listname) {
        this.listname = listname;
    }
}
