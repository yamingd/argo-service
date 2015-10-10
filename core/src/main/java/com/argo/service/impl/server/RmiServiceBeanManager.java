package com.argo.service.impl.server;

import com.argo.service.RmiConfig;
import com.argo.service.listener.ServicePublishListener;
import com.argo.util.IpUtil;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 远程服务管理者.
 *
 * @author yaming_deng
 * 2013-1-21
 */
public class RmiServiceBeanManager implements InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(RmiServiceBeanManager.class);

    public static RmiServiceBeanManager instance;

	private static Map<String, String> beans = new HashMap<String, String>();

    private ServicePublishListener servicePublishListener;

    private static String serviceBaseUrl;
	
	/**
	 * 添加远程服务Bean.
	 * @param beanName
	 * @param serviceName
	 */
	public static void add(String beanName, String serviceName){
		beans.put(beanName, serviceName);
	}

    /**
     * 移除远程服务
     * @param serviceName
     */
    public void remove(String serviceName){
        servicePublishListener.remove(serviceName);
    }

    /**
     * 移除某台服务器
     * @param serviceName
     * @param uri
     */
    public void remove(String serviceName, String uri){
        servicePublishListener.remove(serviceName, uri);
    }
	/**
	 * 
	 * @return Map
	 */
	public Map<String, String> getAll(){
		return beans;
	}

    public void publish(){
        Iterator<Map.Entry<String, String>> itor = beans.entrySet().iterator();
        while (itor.hasNext()){
            Map.Entry<String, String> item = itor.next();
            this.servicePublishListener.publish(item.getValue(), this.serviceBaseUrl);
        }
    }

    public ServicePublishListener getServicePublishListener() {
        return servicePublishListener;
    }

    public void setServicePublishListener(ServicePublishListener servicePublishListener) {
        this.servicePublishListener = servicePublishListener;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (instance != null){
            return;
        }

        Preconditions.checkNotNull(servicePublishListener);

        String[] ipAddress = IpUtil.getHostServerIp();
        this.serviceBaseUrl = ipAddress[0] + ":" + RmiConfig.instance.getPort();
        if (logger.isDebugEnabled()){
            logger.debug("Add Service Endpoint at: " + this.serviceBaseUrl);
        }

        instance = this;

        publish();
    }
}
