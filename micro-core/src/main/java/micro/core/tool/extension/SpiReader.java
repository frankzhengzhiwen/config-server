/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.tool.extension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import micro.core.tool.Tools;

/**
 * 读取Spi文件工具类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月14日
 */
public class SpiReader {
	
	public static Map<String, Class<?>> read(File file) {
		
		Map<String, Class<?>> classes = new HashMap<>();
		try {
			List<String> lines = Tools.File.readLines(file, Charset.forName("UTF-8"));
			for(String line : lines){
				final int ci = line.indexOf('#');
			    if (ci >= 0) line = line.substring(0, ci);
			    line = line.trim();
			    if (line.length() > 0) {
			    	String name = null;
			        int i = line.indexOf('=');
			        if (i > 0) {
			            name = line.substring(0, i).trim();
			            line = line.substring(i + 1).trim();
			        }
			        if (line.length() > 0) {
			        	Class<?> clazz = Class.forName(line, true, SpiReader.class.getClassLoader());
			        	if(i == -1) {
			        		name = Tools.String.uncapitalize(clazz.getSimpleName());
			        	}
			        	if(classes.containsKey(name)) {
			        		throw new SpiLoadException("extension file" + file.getName() + " with duplicated name: " + name);
			        	}
			        	classes.put(name, clazz);
			        }
			    }
			}
		} catch (ClassNotFoundException | IOException e) {
			throw new SpiLoadException("extension file read error: " + file.getName(), e);
		}
        return classes;
	}
}
