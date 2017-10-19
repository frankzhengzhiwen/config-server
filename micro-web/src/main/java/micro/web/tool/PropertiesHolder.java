/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.tool;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * 配置信息静态加载类:
 * 支持所有jar包内的配置文件
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月29日
 */
public class PropertiesHolder {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);

	private static final PropertiesFactoryBean PROPERITES_BEAN = new PropertiesFactoryBean();
	
	/**
	 * 默认配置文件路径：
	 * 1.错误消息配置
	 * 2.系统配置信息
	 */
	private static final String[] DEFAULT_LOCATIONS = {
			"message.properties",
			"system.properties"
	};
	
	static {
		try {
			List<Resource> resources = new ArrayList<>();
			for(String localtion : DEFAULT_LOCATIONS){
				Enumeration<URL> urls = PropertiesHolder.class.getClassLoader().getResources(localtion);
				while(urls.hasMoreElements()){
					resources.add(new UrlResource(urls.nextElement()));
				}
			}
			PROPERITES_BEAN.setLocations(resources.toArray(new Resource[]{}));
			PROPERITES_BEAN.afterPropertiesSet();
		} catch (IOException e) {
			logger.error("load default properties error",  e);
		}
	}
	
	/**
	 * 添加配置信息
	 * @param prop
	 */
	public static void put(Object k, Object v) {
		try {
			PROPERITES_BEAN.getObject().put(k, v);
		} catch (IOException e) {
			logger.error("add properties {}={} error", k, v,  e);
		}
	}
	
	/**
	 * 获取配置消息，默认覆盖原配置
	 * @param key
	 * @return
	 */
	public static Object get(Object key) {
		if(key == null)
			return null;
		try {
			return PROPERITES_BEAN.getObject().get(key);
		} catch (IOException e) {
			logger.error("load properties {} error", DEFAULT_LOCATIONS,  e);
		}
		return null;
	}
	
	/**
	 * 获取配置消息，默认覆盖原配置
	 * @param key
	 * @return
	 */
	public static String getProperty(Object key, Object ... args) {
		if(key == null)
			return null;
		try {
			Object value = PROPERITES_BEAN.getObject().get(String.valueOf(key));
			if(value == null)
				return null;
			return String.format(String.valueOf(value), args);
		} catch (IOException e) {
			logger.error("load properties {} error", DEFAULT_LOCATIONS,  e);
		}
		return null;
	}
}
