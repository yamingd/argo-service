package com.argo.testcases.impl;

import com.argo.testcases.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by yamingd on 9/11/15.
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sayHello(String name) {
        logger.info("hello {}", name);
    }
}
