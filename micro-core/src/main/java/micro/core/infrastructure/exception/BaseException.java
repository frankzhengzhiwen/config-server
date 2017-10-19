package micro.core.infrastructure.exception;

import micro.core.tool.Tools;

/**
 * 基本异常类，初始化基本返回类型
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 1.0.0
 * @date 2015年7月3日
 */
public class BaseException extends RuntimeException {

	private static final long	serialVersionUID	= -3637172659954598718L;

	private String				code;
	
	/**
	 * Creates a new instance of BaseException
	 * 
	 */
	public BaseException() {
		super();
	}

	/**
	 * Creates a new instance of BaseException
	 * 
	 * @param cause
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of BaseException
	 * 
	 * @param message
	 * @param cause
	 */
	public BaseException(String message, Throwable cause) {
		super(Tools.String.isBlank(message) ? "" : message, cause);
	}

	/**
	 * Creates a new instance of BaseException
	 * 
	 * @param message
	 */
	public BaseException(String message) {
		super(Tools.String.isBlank(message) ? "" : message);
	}
	
	public BaseException withCode(int code) {
		this.code = String.valueOf(code);
		return this;
	}
	
	/**
	 * @return property value of code
	 */
	public String code() {
		return code;
	}
}
