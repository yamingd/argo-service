package com.argo.service.beans;

import com.argo.redis.RedisBuket;
import com.argo.redis.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * RedisBucket Bean
 *
 * Created by yaming_deng on 14-8-29.
 */
public class RedisBucketBeanFactory implements FactoryBean<RedisBuket>, InitializingBean, DisposableBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisBuket instance = null;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public RedisBuket getObject() throws Exception {
        return instance;
    }

    @Override
    public Class<?> getObjectType() {
        return RedisBuket.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedisConfig.load();
        instance = RedisBuket.getInstance();
    }

}
