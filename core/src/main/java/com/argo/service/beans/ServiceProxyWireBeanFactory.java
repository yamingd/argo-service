package com.argo.service.beans;

import com.argo.service.RmiConfig;
import com.argo.service.proxy.ServiceClientGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 *
 * 服务定位工厂
 *
 * Created by yaming_deng on 14-8-29.
 */
public class ServiceProxyWireBeanFactory implements FactoryBean<Object>, InitializingBean, DisposableBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<?> serviceClass;
    private String serviceName;
    private Object localService;
    private ServiceClientGenerator generator;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object getObject() throws Exception {
        if (generator != null && RmiConfig.instance.isEnabled()){
            Object o = generator.getService(serviceClass, serviceName);
            return o;
        }else {
            return this.localService;
        }
    }

    @Override
    public Class<?> getObjectType() {
        return this.serviceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.serviceClass);
        Assert.notNull(this.serviceName);
        Assert.notNull(this.generator);

        if (logger.isDebugEnabled()) {
            logger.debug("ServiceProxyWireBeanFactory: serviceName: " + serviceName);
            logger.debug("ServiceProxyWireBeanFactory: localService: " + localService);
            logger.debug("ServiceProxyWireBeanFactory: generator: " + generator);
            logger.debug("ServiceProxyWireBeanFactory: serviceClass: " + serviceClass);
        }
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getLocalService() {
        return localService;
    }

    public void setLocalService(Object localService) {
        this.localService = localService;
    }

    public ServiceClientGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(ServiceClientGenerator generator) {
        this.generator = generator;
    }
}
