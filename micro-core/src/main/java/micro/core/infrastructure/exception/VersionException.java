/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.infrastructure.exception;

import micro.core.infrastructure.message.Message;

/**
 * 数据版本已更新，不能继续操作
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月31日
 */
public class VersionException extends BaseException {

	private static final long serialVersionUID = 2860124998141893107L;

	/**
	 * Creates a new instance of VersionException
	 * 
	 * @param message
	 * @param cause
	 */
	public VersionException(Throwable cause) {
		super(cause);
		withCode(Message.VERSION_ERROR);
	}

	/**
	 * Creates a new instance of VersionException
	 * 
	 * @param message
	 */
	public VersionException() {
		super();
		withCode(Message.VERSION_ERROR);
	}

}
