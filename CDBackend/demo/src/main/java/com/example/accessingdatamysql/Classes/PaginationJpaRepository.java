package com.example.accessingdatamysql.Classes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaginationJpaRepository<T, ID> extends RepositoryPagination<T>, JpaRepository<T, ID> {
}
