package com.example.accessingdatamysql.Classes;

import com.alibaba.fastjson.JSONObject;

public interface JSONPagination {
    JSONObject ListPage(Integer start, Integer end);
}
