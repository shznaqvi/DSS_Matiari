package edu.aku.hassannaqvi.dss_matiari.database.dao;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.abortionCL;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.AbortionCL;
import edu.aku.hassannaqvi.dss_matiari.models.SyncModelNew;

@Dao
public abstract class AbortionCLDao implements BaseDao<AbortionCL> {

    @Query("SELECT * FROM abortionCL")
    public abstract List<AbortionCL> getAllData();

    // This query is used to check if the data in the table exists
    @Query("SELECT * FROM abortionCL Order by id DESC Limit 1")
    public abstract AbortionCL isDataExists();

    @Query("SELECT * FROM abortionCL WHERE uid IN (:uIds)")
    public abstract List<AbortionCL> getAllUnSyncedDataByUIds(List<String> uIds);

    @Query("SELECT * FROM abortionCL WHERE hdssid = :hdssid AND sno = :sno AND round = :round")
    public abstract AbortionCL getAbortionCLByHDSSID(String hdssid, String sno, String round);

    @Query("SELECT * FROM abortionCL WHERE hdssid = :hdssid AND sno like :sno")
    public abstract AbortionCL getAbortionCLByHDSSIDANDSNO(String hdssid, String sno);

    @Query("SELECT * FROM abortionCL WHERE hdssid = :hdssid AND round = :round")
    public abstract boolean isabortionCLsynced(String hdssid, String round);

    // For endless scrolling - Load more functionality
    // LIMIT = Count of records that need to be fetched
    // OFFSET = Starting point in the table from where records that need to be fetched
    @Query("SELECT * FROM abortionCL WHERE hdssid = :hdssid " +
            "ORDER BY id DESC LIMIT :limit OFFSET :offset")
    public abstract List<AbortionCL> getAllDataBy(String hdssid, int limit, int offset);

    // This query is only used for updating sync list
    // id = rowId
    @Query("SELECT * FROM abortionCL WHERE id = :id")
    public abstract AbortionCL getDataById(long id);

    @Query("SELECT * FROM abortionCL WHERE uid = :uid")
    public abstract AbortionCL getDataByUid(String uid);

    @Query("SELECT * FROM abortionCL WHERE sysDate LIKE :date || '%'")
    public abstract List<AbortionCL> getAllByDate(String date);

    // Get total count by username i.e. user specific records count
    @Query("SELECT COUNT(*) FROM abortionCL WHERE username = :username")
    public abstract int getTotalCountByUsername(String username);

    // Get total count
    @Query("SELECT COUNT(*) FROM abortionCL")
    public abstract int getTotalCount();

    @Query("DELETE FROM abortionCL")
    public abstract void deleteAll();

    // Update sync status as success
    public void updateSyncSuccess(List<SyncModelNew.WebResponse> responses) {
        if (responses != null && responses.size() > 0 && responses.get(0).getError() == 0) {
            String syncedDate = DateUtils.getCurrentDateTime();
            String synced = "1";
            for (int i = 0; i < responses.size(); i++) {
                AbortionCL AbortionCLs = getDataById(responses.get(i).getId());
                abortionCL.setSyncDate(syncedDate);
                abortionCL.setSynced(synced);
                abortionCL.setError(false);
                update(abortionCL);
            }
        }
    }

    // Update error status while uploading
    public void updateSyncError(List<AbortionCL> list) {
        for (int i = 0; i < list.size(); i++) {
            AbortionCL obj = list.get(i);
            obj.setError(true);
            update(obj);
        }
    }

}
