package com.argo.service.impl.server;

import com.argo.service.listener.ServicePublishListener;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * 作为同一进程的发布者
 * Created by yaming_deng on 14-9-1.
 */
public class ServiceLocalPublisher implements ServicePublishListener, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean publish(String name, String url) {
        List<String> urls = Lists.newArrayList();
        urls.add(url);
        return true;
    }

    @Override
    public boolean remove(String name, String url) {
        return true;
    }

    @Override
    public boolean remove(String name) {
        List<String> urls = Lists.newArrayList();
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("ServiceLocalPublisher create done.");
    }
}
