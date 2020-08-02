package com.example.accessingdatamysql.entity;

public class ListRequest {

    Integer pageToken; // specific page for result
    Integer pageSize; // size for each page (might not be used)

    public Integer getPageToken() {
        return pageToken;
    }

    public void setPageToken(Integer pageToken) {
        this.pageToken = pageToken;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}