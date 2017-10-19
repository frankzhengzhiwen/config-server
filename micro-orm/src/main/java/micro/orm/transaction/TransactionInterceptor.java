/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.orm.transaction;

import org.aopalliance.intercept.MethodInvocation;

/**
 * TODO
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月28日
 */
@SuppressWarnings("serial")
public class TransactionInterceptor extends org.springframework.transaction.interceptor.TransactionInterceptor {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal = super.invoke(invocation);
		
		return retVal;
	}

}
