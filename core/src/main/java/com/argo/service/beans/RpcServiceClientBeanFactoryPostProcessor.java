package com.argo.service.beans;

import com.argo.service.RmiConfig;
import com.argo.service.ServiceNameBuilder;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

/**
 * 在客户端.
 * 根据RmiService注解来注册服务代理.
 *
 * @author yaming_deng
 * 2013-1-11
 */
public class RpcServiceClientBeanFactoryPostProcessor extends ServiceBeanFactoryPostProcessor {

    private static final String META_INF_RMIS = "META-INF/rmis";

    private ClassLoader classLoader;

    public RpcServiceClientBeanFactoryPostProcessor() {
        super();
        this.classLoader = getClass().getClassLoader();
    }

	@Override
	protected void wrapRmiService(DefaultListableBeanFactory dlbf) {

        if (!RmiConfig.instance.isEnabled()){
            return;
        }

        try {

            Set<String> services = findRMIService(META_INF_RMIS);
            services.addAll(findRMIService("rmis"));

            for (String typeName : services){
                Class<?> type = Class.forName(typeName);
                postAddBean(dlbf, null, type);
            }

        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 遍历远程服务
     * @param resPath
     * @return Set
     */
    private Set<String> findRMIService(String resPath){

        Set<String> stringList = new HashSet<String>();

        try {
            Enumeration<URL> resources = classLoader.getResources(resPath);
            while (resources.hasMoreElements()) {
                Set<String> tmp = getServcieList(resources.nextElement());
                if (tmp != null){
                    stringList.addAll(tmp);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }

        return stringList;
    }

    /**
     *
     * @param dirURL
     * @return String[]
     * @throws URISyntaxException
     */
    private Set<String> getServcieList(URL dirURL) throws URISyntaxException {
        if (dirURL != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("dirURL: protocol={}, {}", dirURL.getProtocol(), dirURL);
            }
            Set<String> services = new HashSet<String>();
            String protocol = dirURL.getProtocol();
            if (protocol.equalsIgnoreCase("file")){
                String[] tmp = new File(dirURL.toURI()).list();
                services.addAll(Lists.newArrayList(tmp));
            }else if (protocol.equalsIgnoreCase("jar")){

                FileSystem fileSystem = null;
                try {
                    fileSystem = FileSystems.newFileSystem(dirURL.toURI(), Collections.<String, Object>emptyMap());
                    Path path = fileSystem.getPath(META_INF_RMIS);
                    DirectoryStream<Path> items = Files.newDirectoryStream(path);
                    for (Path item : items){
                        services.add(item.getFileName().toString());
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }

            }

            if (logger.isDebugEnabled()) {
                logger.debug("dirURL: {} services: \n {}", dirURL, services);
            }

            return services;
        }
        return null;
    }

    @Override
	protected void postAddBean(DefaultListableBeanFactory dlbf, String beanName,
			Class<?> clzz) {

		Class<?> serviceInterface = clzz;

		String serviceName = ServiceNameBuilder.get(serviceInterface, null);

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServiceProxyWireBeanFactory.class.getName());
		
		builder.addPropertyValue("serviceClass", serviceInterface);
        builder.addPropertyValue("serviceName", serviceName);
        if (beanName != null) {
            builder.addPropertyReference("localService", beanName);
        }
        builder.addPropertyReference("generator", "rmiServiceClientGenerator");
		
		dlbf.registerBeanDefinition(serviceName, builder.getBeanDefinition());

		if (logger.isDebugEnabled()) {
			logger.debug("@@@wrapRmiServiceProxy-postProcessBeanFactory, beanName={}, serviceName={}", beanName, serviceName);
		}

	}

}
