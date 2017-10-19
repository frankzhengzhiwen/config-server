/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.extension.support;

import micro.core.extension.spi.HolderClearer;

/**
 * 场景编码/业务编码绑定线程变量
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月7日
 */
public class BusinessCodeHolder {
	
	private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();
	
	public static void set(String code){
		HOLDER.set(code);
	}
	
	public static String get(){
		return HOLDER.get();
	}
	
	public static void clear(){
		HOLDER.remove();
	}
	
	public static class BusinessCodeClearer implements HolderClearer{

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clear() {
			BusinessCodeHolder.clear();
		}
		
	}
}
