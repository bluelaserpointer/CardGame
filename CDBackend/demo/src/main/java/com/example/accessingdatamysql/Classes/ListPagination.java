package com.example.accessingdatamysql.Classes;

import java.util.List;

public interface ListPagination<T> {
    List<T> ListPage(Integer start, Integer end);
}
