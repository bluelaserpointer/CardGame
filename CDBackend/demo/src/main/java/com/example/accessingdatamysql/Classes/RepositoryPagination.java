package com.example.accessingdatamysql.Classes;

import java.util.List;

public interface RepositoryPagination<T> {
    List<T> ListPage(Integer start, Integer end);
}
