/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.extension.spi;

/**
 * Web请求拦截器：
 * 失败自行抛出异常
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月21日
 */
public interface SpiPreHandler {

	/**
	 */
	void preHandle();
}
