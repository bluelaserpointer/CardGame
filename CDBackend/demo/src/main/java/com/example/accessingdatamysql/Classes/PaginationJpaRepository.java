package com.example.accessingdatamysql.Classes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaginationJpaRepository<T, ID> extends JpaRepository<T, ID> {
    List<T> ListPage(Integer start, Integer end);
}
