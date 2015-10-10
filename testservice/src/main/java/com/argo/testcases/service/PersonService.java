package com.argo.testcases.service;

import com.argo.annotation.RmiService;

/**
 * Created by yamingd on 9/11/15.
 */
@RmiService
public interface PersonService {

    /**
     *
     * @param name
     */
    void sayHello(String name);
}
