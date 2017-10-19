/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.extension.spi;

import java.util.List;

import micro.core.tool.extension.MicroExtensionLoader;

/**
 * 清理所有保存的线程栈数据
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月5日
 */
public interface HolderClearer {
	
	void clear();

	/**
	 * 清除所有线程
	 */
	static void clearAll(){
		List<HolderClearer> clearers = MicroExtensionLoader
						.getInstance(HolderClearer.class).get();
		for(HolderClearer clearer : clearers) {
			clearer.clear();
		}
	}
}
