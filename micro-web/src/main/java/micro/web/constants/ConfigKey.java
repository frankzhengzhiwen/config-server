/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.constants;

/**
 * 配置信息主键
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月30日
 */
public class ConfigKey {
	
	/**
	 * set http header of remote response error code 
	 */
	public static final String REMOTE_SERVICE_KEY = "Remote-Key";
	public static final String REMOTE_SERVICE_REQUEST = "request";
//	public static final String REMOTE_SERVICE_RESPONSE_OK = "ok";
	public static final String REMOTE_SERVICE_RESPONSE_CUSTOM_ERROR = "custom";
	public static final String REMOTE_SERVICE_RESPONSE_OTHER_ERROR = "other";
	
	public static final String REMOTE_TRACE_KEY = "trace";
	
	public static final String HTTP_UNKNOWN = "unknown";
}
