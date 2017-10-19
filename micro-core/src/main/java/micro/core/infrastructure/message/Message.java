/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */

package micro.core.infrastructure.message;

import org.springframework.http.HttpStatus;

/**
 * 错误消息定义类：
 * 主键最后字段设置到响应头中
 * 
 * @author <a href=mailto:frankzhiwen@163.com>郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月22日
 */
public class Message {

	/**
	 * 200 OK: 操作成功
	 */
	public static final int SUCCESS 				= HttpStatus.OK.value();
	
	/**
	 * 400 Bad Request: 请求错误：%s
	 */
	public static final int BAD_REQUEST 		= HttpStatus.BAD_REQUEST.value();
	
	/**
	 * 401 Unauthorized: 未经授权的用户凭证%s
	 */
	public static final int UNAUTHORIZED 		= HttpStatus.UNAUTHORIZED.value();
	
	/**
	 * 403 Forbidden: 禁止访问：%s
	 */
	public static final int FORBIDDEN 		= HttpStatus.FORBIDDEN.value();
	
	/**
	 * 418 I'm a teapot: 请求参数错误%s：%s
	 */
	public static final int PARAMETER_ERROR 		= HttpStatus.I_AM_A_TEAPOT.value();
	
	/**
	 * 409 Conflict: 业务异常%s：%s
	 */
	public static final int BUSINESS_ERROR 		= HttpStatus.CONFLICT.value();
	
	/**
	 * 410 Gone: 在您的操作之前，该数据已被更新，请刷新后再重新操作：%s
	 */
	public static final int VERSION_ERROR 		= HttpStatus.GONE.value();

	/**
	 * 417 Expectation Failed: 重复操作，该数据已是最新：%s
	 */
	public static final int REPEATED_ERROR 	= HttpStatus.EXPECTATION_FAILED.value();

	/**
	 * 506 Variant Also Negotiates: 系统错误%s：%s
	 */
	public static final int SYSTEM_ERROR 		= HttpStatus.VARIANT_ALSO_NEGOTIATES.value();

	/**
	 * 501 Not Implemented: 内部服务调用出错：%s
	 */
	public static final int REMOTE_ERROR 		= HttpStatus.NOT_IMPLEMENTED.value();
	
	/**
	 * 500 Internal Server Error: 服务器内部错误
	 */
	public static final int INTERNAL_SERVER_ERROR 		= HttpStatus.INTERNAL_SERVER_ERROR.value();
	
	/**
	 * 503 Service Unavailable: 服务%s，请稍后再试
	 */
	public static final int SERVICE_UNAVAILABLE 		= HttpStatus.SERVICE_UNAVAILABLE.value();

}
