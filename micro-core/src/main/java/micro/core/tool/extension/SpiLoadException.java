/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.tool.extension;

/**
 * SPI加载异常类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月14日
 */
public class SpiLoadException extends RuntimeException {

	private static final long serialVersionUID = 3166366923805958621L;

	/**
	 * Creates a new instance of SpiLoadException
	 * @param message
	 * @param cause
	 */
	public SpiLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of SpiLoadException
	 * @param message
	 */
	public SpiLoadException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance of SpiLoadException
	 * @param cause
	 */
	public SpiLoadException(Throwable cause) {
		super(cause);
	}

}
