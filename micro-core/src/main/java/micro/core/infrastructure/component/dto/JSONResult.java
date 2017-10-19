/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.infrastructure.component.dto;

import java.io.Serializable;

/**
 * JSON统一响应包装类：
 * 正常返回数据及自定义异常返回封装
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月22日
 */
public class JSONResult<T> implements Serializable {

	private static final long serialVersionUID = -7562242513741230531L;

	/**
	 * 是否调用成功
	 */
	private boolean success;
	
	/**
	 * 接口编码或状态码
	 */
	private String code;
    
    /**
     * 响应消息
     */
    private String message;

	/**
	 * 响应数据
	 */
	private T data;
    
    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
