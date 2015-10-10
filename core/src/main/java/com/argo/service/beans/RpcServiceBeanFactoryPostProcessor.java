package com.argo.service.beans;

import com.argo.annotation.RmiService;
import com.argo.service.RmiConfig;
import com.argo.service.ServiceNameBuilder;
import com.argo.service.impl.server.RmiServiceBeanManager;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * 在服务端.
 * 根据RmiService注解来注册服务
 * @author yaming_deng
 * 2013-1-11
 */
public class RpcServiceBeanFactoryPostProcessor extends ServiceBeanFactoryPostProcessor {

    @Override
	protected void postAddBean(DefaultListableBeanFactory dlbf, String beanName,
			Class<?> clzz) {

        if (!RmiConfig.instance.isEnabled()){
            return;
        }

        Class<?>[] types = clzz.getInterfaces();

        if (types.length == 0){
            return;
        }

        Class<?> serviceInterface = types[0];

		RmiService annotation = serviceInterface.getAnnotation(RmiService.class);
		
		if(annotation==null){
			return;
		}

        String serviceName = ServiceNameBuilder.get(serviceInterface, annotation.servcieName());

		if (logger.isDebugEnabled()) {
			logger.debug("@@@wrapRmiService-postProcessBeanFactory0, beanName={}, serviceName={}", beanName, serviceName);
		}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RmiServiceExporter.class.getName());
		
		builder.addPropertyValue("serviceInterface", serviceInterface);
		builder.addPropertyReference("service", beanName);
		builder.addPropertyValue("serviceName", serviceName);
		builder.addPropertyValue("registryPort", annotation.port());
		builder.addPropertyValue("replaceExistingBinding", annotation.replaceExistingBinding());
		builder.addPropertyValue("alwaysCreateRegistry", annotation.alwaysCreateRegistry());
		
		dlbf.registerBeanDefinition(serviceName+"Proxy", builder.getBeanDefinition());

		// 加入管理
		RmiServiceBeanManager.add(beanName, serviceName);

		if (logger.isDebugEnabled()) {
			logger.debug("@@@wrapRmiService-postProcessBeanFactory1, beanName={}, serviceName={}", beanName, serviceName);
		}
	}

}
