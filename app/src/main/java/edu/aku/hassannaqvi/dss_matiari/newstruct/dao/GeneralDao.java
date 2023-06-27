package edu.aku.hassannaqvi.dss_matiari.newstruct.dao;

import androidx.room.Dao;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface GeneralDao {

    @RawQuery
    List<String> getUnsyncedDataUIds(SupportSQLiteQuery query);

}
