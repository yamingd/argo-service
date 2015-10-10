package com.argo.testcases.impl;

import com.argo.testcases.service.PersonService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yamingd on 9/11/15.
 */
@Component
public class AccountManager implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonService personService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(personService);
        logger.info("PersonService: {}", personService);
        personService.sayHello("David Dear. from Account Manager");
    }

}
