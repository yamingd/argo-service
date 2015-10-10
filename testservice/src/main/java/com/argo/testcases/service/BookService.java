package com.argo.testcases.service;

import com.argo.annotation.RmiService;
import com.argo.collection.Pagination;

/**
 * Created by yamingd on 9/11/15.
 */
@RmiService
public interface BookService {

    /**
     *
     * @param resultSet
     * @param bookId
     * @return
     */
    Pagination<Integer> findBy(Pagination<Integer> resultSet, Integer bookId);

}
