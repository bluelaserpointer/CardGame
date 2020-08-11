package com.example.accessingdatamysql.Classes;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.function.UnaryOperator;

public interface PaginationDao {
    JSONObject ListPage(Integer start, Integer end);
    default <T, ID> JSONObject ListPage(Integer page_token, Integer page_size, PaginationJpaRepository<T, ID> repository, UnaryOperator<T> detailAdder) {
        final JSONObject response = new JSONObject();
        final int repositoryCount = (int)repository.count();

        // get the result
        final List<T> elements = repository.ListPage((page_token - 1) * page_size, page_size);
        if(detailAdder != null) {
            elements.replaceAll(detailAdder);
        }
        response.put("result", elements);

        // get the nextPageToken
        if ((repositoryCount - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            response.put("nextPageToken", page_token + 1);
        }

        // get the total pages of the result
        int totalPages = repositoryCount / page_size;
        if ((repositoryCount - page_size * totalPages) > 0) {
            ++totalPages;
        }
        response.put("totalPages", totalPages);

        return response;
    }
    default <T, ID> JSONObject ListPage(Integer page_token, Integer page_size, PaginationJpaRepository<T, ID> repository) {
        return this.ListPage(page_token, page_size, repository, null);
    }
}
