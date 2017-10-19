/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.tool.extension;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Spring容器SPI扩展接口加载类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月15日
 */
public class SpringExtensionLoader {

	private static ApplicationContext applicationContext;
	
	public <T> List<T> get(Class<T> clazz) {
		List<T> beans = new ArrayList<>();
		applicationContext.getBeansOfType(clazz).forEach((name, bean)->{
			beans.add(bean);
		});
		return beans;
	}
	
	public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringExtensionLoader.applicationContext = applicationContext;
	}
}
