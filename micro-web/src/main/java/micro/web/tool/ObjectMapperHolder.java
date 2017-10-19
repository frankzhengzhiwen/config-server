/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.tool;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 在字段权限控制时，需要初始化ObjectMapper
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年9月5日
 */
public class ObjectMapperHolder {

private static final ThreadLocal<ObjectMapper> HOLDER = new ThreadLocal<>();
	
	public static void set(ObjectMapper mapper){
		HOLDER.set(mapper);
	}
	
	public static ObjectMapper get(){
		return HOLDER.get();
	}
	
	public static void clear(){
		HOLDER.remove();
	}
}
