package com.argo.service.listener;

/**
 * 远程服务池变化响应.
 * @author yaming_deng
 *
 */
public interface ServicePublishListener {
	

	/**
	 * publish services out for discovery.
	 * 
	 * @param name 名字, 可以为javaBean名称
	 * @param url 连接URL. 如rmi://127.0.0.1:6600/
	 * @return boolean
	 */
	boolean publish(String name, String url);

	/**
	 * 取消发布.
	 * @param name
	 * @param url
	 * @return boolean
	 */
	boolean remove(String name, String url);

    /**
     * 移除服务
     * @param name
     * @return boolean
     */
	boolean remove(String name);
}
