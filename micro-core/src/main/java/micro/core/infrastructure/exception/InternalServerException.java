package micro.core.infrastructure.exception;

import micro.core.infrastructure.message.Message;

/**
 * ClassName: InternalServerException
 * 
 * @description: 服务器内部错误：%s
 * 
 * @author 郑智文
 * @version 1.0
 * @CreateDate 2015-9-29 下午1:45:05
 */
public class InternalServerException extends BaseException {

	private static final long serialVersionUID = -1134989073615501804L;

	/**
	 * Creates a new instance of InternalServerException
	 * 
	 * @param message
	 * @param cause
	 */
	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
		withCode(Message.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Creates a new instance of InternalServerException
	 * 
	 * @param message
	 */
	public InternalServerException(String message) {
		super(message);
		withCode(Message.INTERNAL_SERVER_ERROR);
	}
	
}
