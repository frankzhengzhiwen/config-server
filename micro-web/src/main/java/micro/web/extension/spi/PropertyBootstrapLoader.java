/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.extension.spi;

import org.springframework.core.env.PropertySource;

/**
 * 启动加载并设置配置信息接口
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月17日
 */
public interface PropertyBootstrapLoader {

	/**
	 * @return 需要加载的配置信息
	 */
	PropertySource<?> load();
}
