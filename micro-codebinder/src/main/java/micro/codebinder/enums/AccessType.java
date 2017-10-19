/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.enums;

/**
 * 接口访问控制类型
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年9月6日
 */
public enum AccessType {

	/**
	 * 默认类型：严格的权限控制
	 */
	Strict,
	/**
	 * 允许所有访问
	 */
	AllAllowed,
	/**
	 * 允许登录用户访问
	 */
	LoginUserAllowed,
	/**
	 * 临时不可用（停机运维、接口bug等）
	 */
	TmpUnloaded
}
