package micro.core.infrastructure.exception;

import micro.core.infrastructure.message.Message;

/**
 * 与用户操作无关,由程序或网络等原因造成的异常<br>
 * 
 * @author 郑智文
 * @version 2014-09
 */
public class SystemException extends BaseException {

	private static final long	serialVersionUID	= 8989828988277681296L;

	/**
	 * Creates a new instance of SystemException
	 * 
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
		withCode(Message.SYSTEM_ERROR);
	}

	/**
	 * Creates a new instance of SystemException
	 * 
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
		withCode(Message.SYSTEM_ERROR);
	}

}
