package com.argo.service.beans;

import com.argo.service.RmiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.io.IOException;

/**
 * 根据RmiService来自动创建一般Service
 *
 * @author yaming_deng
 * 2013-1-11
 */
public class ServiceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServiceBeanFactoryPostProcessor() {

		try {
			RmiConfig.load();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

    /* (non-Javadoc)
         * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
         */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
		if (!(beanFactory instanceof DefaultListableBeanFactory)) {
			throw new IllegalStateException(
					"CustomAutowireConfigurer needs to operate on a DefaultListableBeanFactory");
		}
		
		DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
		
		wrapRmiService(dlbf);
	}

	protected void wrapRmiService(DefaultListableBeanFactory dlbf) {
		
		String[] beanNames = dlbf.getBeanDefinitionNames();
		
		for (String beanName : beanNames) {
			BeanDefinition def = dlbf.getBeanDefinition(beanName);
			if(def.getBeanClassName()==null || !(def instanceof AbstractBeanDefinition)){
				continue;
			}
			try {
				Class<?> beanClass = Class.forName(def.getBeanClassName());
				this.postAddBean(dlbf, beanName, beanClass);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
	}

	protected void postAddBean(DefaultListableBeanFactory dlbf, String beanName,
			Class<?> clzz) {


	}

}
