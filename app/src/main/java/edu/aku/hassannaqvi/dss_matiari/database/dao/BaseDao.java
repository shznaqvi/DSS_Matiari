package edu.aku.hassannaqvi.dss_matiari.database.dao;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {

    // IGNORE = If conflict occur then it won't throw exception but return rowId -1
    @Insert(onConflict = IGNORE)
    long add(T obj);

    @Insert(onConflict = IGNORE)
    void addAll(T[] list);

    @Update(onConflict = IGNORE)
    int update(T obj);

    @Update
    void updateAll(List<T> list);

    @Delete
    void delete(T obj);
}
