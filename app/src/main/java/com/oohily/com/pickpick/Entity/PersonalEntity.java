package com.oohily.com.pickpick.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.oohily.com.pickpick.gen.DaoSession;
import com.oohily.com.pickpick.gen.ListEntityDao;
import com.oohily.com.pickpick.gen.PersonalEntityDao;

/**
 * Created by Zhongsz on 2017/7/24.
 */
@Entity
public class PersonalEntity {
    @Id
    private Long id;
    @NotNull
    private String username;
    private String listname;
    @ToMany(joinProperties = {
            @JoinProperty(name = "listname",
                    referencedName = "listname")
    })
    @OrderBy("id ASC")
    private List<ListEntity> listEntities;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 380296787)
    private transient PersonalEntityDao myDao;
    @Generated(hash = 606852068)
    public PersonalEntity(Long id, @NotNull String username, String listname) {
        this.id = id;
        this.username = username;
        this.listname = listname;
    }
    @Generated(hash = 300561008)
    public PersonalEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getLsitname() {
        return this.listname;
    }
    public void setLsitname(String lsitname) {
        this.listname = lsitname;
    }
    public String getListname() {
        return this.listname;
    }
    public void setListname(String listname) {
        this.listname = listname;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1963624565)
    public List<ListEntity> getListEntities() {
        if (listEntities == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListEntityDao targetDao = daoSession.getListEntityDao();
            List<ListEntity> listEntitiesNew = targetDao
                    ._queryPersonalEntity_ListEntities(listname);
            synchronized (this) {
                if (listEntities == null) {
                    listEntities = listEntitiesNew;
                }
            }
        }
        return listEntities;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1130512128)
    public synchronized void resetListEntities() {
        listEntities = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1370523178)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPersonalEntityDao() : null;
    }
}
