/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.infrastructure.component.dto;

import java.util.Date;

/**
 * SpringBoot默认返回错误信息为Map<String, Object>
 * 解析时使用实体对象
 * 	{@link org.springframework.boot.autoconfigure.web.BasicErrorController
 * 			#error(javax.servlet.http.HttpServletRequest)}
 * 	{@link micro.core.web.controller.BasicErrorController#getErrorAttributes(HttpServletRequest)}
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月31日
 */
public class ErrorInfo {

	/**
	 * 时间戳
	 */
	private Date timestamp;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 错误信息
	 */
	private String error;
	
	/**
	 * 异常类
	 */
	private String exception;
	
	/**
	 * 异常消息
	 */
	private String message;
	
	/**
	 * 请求调用链
	 */
	private String requestChain;
	
	/**
	 * 堆栈信息
	 */
	private String trace;
	
	/**
	 * 请求路径
	 */
	private String path;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestChain() {
		return requestChain;
	}

	public void setRequestChain(String requestChain) {
		this.requestChain = requestChain;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
