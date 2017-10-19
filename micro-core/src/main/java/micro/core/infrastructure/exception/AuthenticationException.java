/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.infrastructure.exception;

import micro.core.infrastructure.message.Message;

/**
 * 权限验证错误
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月8日
 */
public class AuthenticationException extends BaseException {

	private static final long serialVersionUID = 7576419010034232089L;

	/**
	 * Creates a new instance of AuthenticationException
	 * 
	 * @param message
	 * @param cause
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
		withCode(Message.FORBIDDEN);
	}

	/**
	 * Creates a new instance of AuthenticationException
	 * 
	 * @param message
	 */
	public AuthenticationException(String message) {
		super(message);
		withCode(Message.FORBIDDEN);
	}

}
