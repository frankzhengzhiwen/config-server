/**
 * Copyright (c) 2016, micro All Rights Reserved.
 */  
 
package micro.core.util;

/**
 * 基本对象工具类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 1.0.0
 * @date 2016年7月7日
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	public static String nullSafeString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
}
