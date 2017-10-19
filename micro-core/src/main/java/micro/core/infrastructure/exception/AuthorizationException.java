/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.infrastructure.exception;

import micro.core.infrastructure.message.Message;

/**
 * 未经授权的用户凭证异常
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月17日
 */
public class AuthorizationException extends BaseException {

	private static final long serialVersionUID = -1424305490545507065L;

	/**
	 * Creates a new instance of AuthorizationException
	 * 
	 */
	public AuthorizationException() {
		this("");
	}

	/**
	 * Creates a new instance of AuthorizationException
	 * 
	 * @param message
	 * @param cause
	 */
	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
		withCode(Message.UNAUTHORIZED);
	}

	/**
	 * Creates a new instance of AuthorizationException
	 * 
	 * @param message
	 */
	public AuthorizationException(String message) {
		super(message);
		withCode(Message.UNAUTHORIZED);
	}

	/**
	 * Creates a new instance of AuthorizationException
	 * 
	 * @param cause
	 */
	public AuthorizationException(Throwable cause) {
		this("", cause);
	}

}
