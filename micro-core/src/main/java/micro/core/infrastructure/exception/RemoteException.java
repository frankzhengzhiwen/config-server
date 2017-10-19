/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.infrastructure.exception;

import micro.core.infrastructure.component.dto.ErrorInfo;
import micro.core.infrastructure.message.Message;
import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * 远程调用异常
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月29日
 */
public class RemoteException extends HystrixBadRequestException {

	private static final long serialVersionUID = -4797344137677251038L;
	
	private String				code;

	private ErrorInfo			errorInfo;

	/**
	 * Creates a new instance of RemoteException
	 * 
	 * @param message
	 * @param cause
	 */
	public RemoteException(String message, Throwable cause) {
		super(message, cause);
		withCode(Message.REMOTE_ERROR);
	}

	/**
	 * Creates a new instance of RemoteException
	 * 
	 * @param message
	 */
	public RemoteException(String message) {
		super(message);
		withCode(Message.REMOTE_ERROR);
	}

	public RemoteException withCode(int code) {
		this.code = String.valueOf(code);
		return this;
	}
	
	/**
	 * @return property value of code
	 */
	public String code() {
		return code;
	}
	
	public RemoteException withErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
		return this;
	}

	public ErrorInfo errorInfo(){
		return errorInfo;
	}

}
