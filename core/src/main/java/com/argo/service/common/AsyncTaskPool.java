package com.argo.service.common;

import com.argo.service.pool.TrackingThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by user on 12/27/14.
 */
@Component
public class AsyncTaskPool implements InitializingBean{

    public static final Logger logger = LoggerFactory.getLogger(AsyncTaskPool.class);

    private int cores = 0;
    private TrackingThreadPoolTaskExecutor pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        cores = Runtime.getRuntime().availableProcessors();
        this.pool = createPool();
        logger.info("AsyncTaskPool Created.");
    }

    protected TrackingThreadPoolTaskExecutor createPool() {
        TrackingThreadPoolTaskExecutor exe = new TrackingThreadPoolTaskExecutor();
        exe.setMaxPoolSize(this.cores);
        exe.setWaitForTasksToCompleteOnShutdown(true);
        exe.afterPropertiesSet();
        return exe;
    }

    public TrackingThreadPoolTaskExecutor getPool(){
        return pool;
    }

}
