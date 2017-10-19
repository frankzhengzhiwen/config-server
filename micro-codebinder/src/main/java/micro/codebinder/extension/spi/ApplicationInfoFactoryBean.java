/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.extension.spi;

import org.springframework.beans.factory.FactoryBean;

import micro.codebinder.model.ApplicationInfo;

/**
 * 应用信息加载接口类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月17日
 */
public interface ApplicationInfoFactoryBean extends FactoryBean<ApplicationInfo> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Class<?> getObjectType() {
		return ApplicationInfo.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isSingleton() {
		return true;
	}
}
