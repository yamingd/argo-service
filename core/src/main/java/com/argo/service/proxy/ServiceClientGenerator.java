package com.argo.service.proxy;

import org.springframework.beans.factory.InitializingBean;

/**
 * 描述 ：
 *
 * @author yaming_deng
 * 2013-1-11
 */
public interface ServiceClientGenerator extends InitializingBean {

	/**
	 * 服务协议
	 * @return String
	 */
    String getProtocalMark();

	/**
	 * 构造服务名
	 * @param val
	 * @return String
	 */
    String stripServiceName(String val);
	/**
	 * 返回服务代理
	 *
	 * @param <T>
	 * @param clazz
	 * @return T
	 */
	<T> T getService(Class<T> clazz);
	
	/**
	 * 返回服务代理
	 *
	 * @param <T>
	 * @param clazz
	 * @param serviceName
	 * @return T
	 */
	<T> T getService(Class<T> clazz, String serviceName);
}
