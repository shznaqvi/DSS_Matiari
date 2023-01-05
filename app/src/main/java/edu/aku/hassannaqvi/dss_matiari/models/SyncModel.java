package edu.aku.hassannaqvi.dss_matiari.models;

import androidx.room.Entity;

import org.apache.commons.lang3.StringUtils;

public class SyncModel {
    String tableTitle;
    String tableName;
    String status;
    String info;
    int statusID;
    String message;
    String filter = null;
    String select = null;

    public SyncModel(String tableName) {

        this.tableName = tableName;
        this.tableTitle = tableName.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
        this.status = StringUtils.EMPTY;
        this.statusID = 0;
        this.message = StringUtils.EMPTY;


    }

    public SyncModel(String tableName, String select) {

        this.tableName = tableName;
        this.tableTitle = tableName.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
        this.status = StringUtils.EMPTY;
        this.statusID = 0;
        this.message = StringUtils.EMPTY;
        this.select = select;
    }

    public SyncModel(String tableName, String select, String filter) {

        this.tableName = tableName;
        this.tableTitle = tableName.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
        this.status = StringUtils.EMPTY;
        this.statusID = 0;
        this.message = StringUtils.EMPTY;
        this.select = select;
        this.filter = filter;

    }

    public String getTableTitle() {
        return tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }


    public String gettableName() {
        return tableName;
    }

    public void settableName(String tableName) {
        this.tableName = tableName;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getstatusID() {
        return statusID;
    }

    public void setstatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public String getFilter() {

        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
