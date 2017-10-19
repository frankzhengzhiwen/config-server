/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.extension.spi;

import java.util.List;

/**
 * 启动获取并绑定配置信息接口
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月30日
 */
public interface PropertyBootstrapBinder {

	/**
	 * @return 需要读取的已经加载的配置信息主键
	 * 可统一通过PropertyHolder.get(Object)/getProperty(Object, Object...)获取信息
	 */
	List<String> propertyKeys();
	
}
