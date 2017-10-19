package micro.core.infrastructure.exception;

import micro.core.infrastructure.message.Message;

/**
 * 与用户操作或实际业务逻辑相关的异常<br>
 * 由框架统一捕获并处理,将异常信息显示给用户
 * 
 * @author 郑智文
 * @version 2014-09
 */
public class BusinessException extends BaseException {

	private static final long	serialVersionUID	= -4942849136285353014L;

	/**
	 * Creates a new instance of BusinessException
	 * 
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		withCode(Message.BUSINESS_ERROR);
	}

	/**
	 * Creates a new instance of BusinessException
	 * 
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
		withCode(Message.BUSINESS_ERROR);
	}

}
