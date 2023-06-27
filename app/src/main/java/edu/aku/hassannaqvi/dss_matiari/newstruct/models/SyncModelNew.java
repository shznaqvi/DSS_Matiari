package edu.aku.hassannaqvi.dss_matiari.newstruct.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.dss_matiari.room.DssRoomDatabase;

public class SyncModelNew {

    /*For DOWNLOAD*/
    private String table;
    private String filter;
    private String select;
    private String check;

    // For Sync list adapter
    private int id;
    private int statusId;
    private String status;
    private String message;
    private String info;

    // For App Version
    // Path of output-meta json file of apk upload server
    private String folder;

    //For sections in upload data
    private String tableSections;

    public SyncModelNew() {
    }

    public SyncModelNew(String table, String tableSections) {
        this.table = table;
        this.tableSections = tableSections;
    }

    public SyncModelNew(String table, String select, String filter, String check) {
        this.table = table;
        this.select = select;
        this.filter = filter;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public String getFilter() {
        return filter;
    }

    public String getSelect() {
        return select;
    }

    public String getCheck() {
        return check;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getTableSections() {
        return tableSections;
    }

    public void setTableSections(String tableSections) {
        this.tableSections = tableSections;
    }

    /*For generating sync adapter item*/
    // This list is used to display ALL items in the list while downloading
    public static List<SyncModelNew> initSyncList(List<String> syncTableList) {
        List<SyncModelNew> syncModelNewList = new ArrayList<>();
        for (int i = 0; i < syncTableList.size(); i++) {
            syncModelNewList.add(new SyncModelNew(syncTableList.get(i), "", "", ""));
        }
        return syncModelNewList;
    }

    /*Used to parse UPLOAD response*/
    public static class WebResponse {
        private int id;
        private int status;
        private int error;
        private String message;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // For Date Time Parsing because JsonObject is returning from server
    public static class ResponseDate implements Serializable {
        private String date;

        @SerializedName("timezone_type")
        private int timezoneType;

        private String timezone;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getTimezoneType() {
            return timezoneType;
        }

        public void setTimezoneType(int timezoneType) {
            this.timezoneType = timezoneType;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        // This class is used to parse the object to save in room db
        public static class DataConverter extends DssRoomDatabase.BaseConverter<ResponseDate> {
            public DataConverter() {
                super(new TypeToken<ResponseDate>() {
                }.getType());
            }
        }
    }

}
