/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.extension.spi;

/**
 * 客户端请求结果处理器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月28日
 */
public interface ApiResultHandler {

	Object handle(Object result);
}
