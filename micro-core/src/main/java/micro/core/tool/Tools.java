/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.tool;

import micro.core.util.AnnotationUtils;
import micro.core.util.ArrayUtils;
import micro.core.util.BeanUtils;
import micro.core.util.BooleanUtils;
import micro.core.util.ClassUtils;
import micro.core.util.CollectionUtils;
import micro.core.util.DateFormatUtils;
import micro.core.util.DateUtils;
import micro.core.util.DigestUtils;
import micro.core.util.ExceptionUtils;
import micro.core.util.FileUtils;
import micro.core.util.IOUtils;
import micro.core.util.JSONUtils;
import micro.core.util.ListUtils;
import micro.core.util.MapUtils;
import micro.core.util.NumberUtils;
import micro.core.util.ObjectUtils;
import micro.core.util.ReflectionUtils;
import micro.core.util.SerializationUtils;
import micro.core.util.SetUtils;
import micro.core.util.StringUtils;
import micro.core.util.TypeUtils;

/**
 * 通用工具类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月16日
 */
public class Tools {
	
	/**
	 * 数组工具类
	 */
	public static class Array extends ArrayUtils{}
	
	/**
	 * 注解工具类
	 */
	public static class Annotation extends AnnotationUtils{}
	
	/**
	 * 通用断言类
	 */
	public static class Assert extends micro.core.util.Assert{}
	
	/**
	 * java bean对象工具类
	 */
	public static class Bean extends BeanUtils{}
	
	/**
	 * 布尔值工具类
	 */
	public static class Boolean extends BooleanUtils{}
	
	/**
	 * java类 操作工具类
	 */
	public static class Class extends ClassUtils{}
	
	/**
	 * 集合操作工具类
	 */
	public static class Collection extends CollectionUtils{}
	
	/**
	 * 日期格式化操作工具类
	 */
	public static class DateFormat extends DateFormatUtils{}
	
	/**
	 * 日期解析工具类
	 */
	public static class DateFormats extends micro.core.util.DateFormats{}
	
	/**
	 * 默认支持的日期格式
	 */
	public static class  DatePattern{
		/**
		 * yyyy-MM-dd
		 */
		public static final java.lang.String TILL_DAY_1 = "yyyy-MM-dd";
		/**
		 * yyyyMMdd
		 */
		public static final java.lang.String TILL_DAY_2 = "yyyyMMdd";
	    /**
	     * yyyy-MM-dd HH:mm
	     */
		public static final java.lang.String TILL_MINUTE_1 = "yyyy-MM-dd HH:mm";
	    /**
	     * yyyyMMdd HH:mm
	     */
		public static final java.lang.String TILL_MINUTE_2 = "yyyyMMdd HH:mm";
	    /**
	     * yyyyMMddHHmm
	     */
		public static final java.lang.String TILL_MINUTE_3 = "yyyyMMddHHmm";
	    /**
	     * yyyy-MM-dd HH:mm:ss
	     */
		public static final java.lang.String TILL_SECOND_1 = "yyyy-MM-dd HH:mm:ss";
	    /**
	     * yyyyMMdd HH:mm:ss
	     */
		public static final java.lang.String TILL_SECOND_2 = "yyyyMMdd HH:mm:ss";
	    /**
	     * yyyyMMddHHmmss
	     */
		public static final java.lang.String TILL_SECOND_3 = "yyyyMMddHHmmss";
	    /**
	     * yyyy-MM-dd HH:mm:ss:SSS
	     */
		public static final java.lang.String TILL_MILLISECOND_1 = "yyyy-MM-dd HH:mm:ss:SSS";
	    /**
	     * yyyyMMdd HH:mm:ss:SSS
	     */
		public static final java.lang.String TILL_MILLISECOND_2 = "yyyyMMdd HH:mm:ss:SSS";
	    /**
	     * yyyyMMdd HH:mm:ss:SSS
	     */
		public static final java.lang.String TILL_MILLISECOND_3 = "yyyyMMddHHmmssSSS";
		
	}
	
	/**
	 * 日期操作工具类
	 */
	public static class Date extends DateUtils{}
	
	/**
	 * 加密解密工具类
	 */
	public static class Digest extends DigestUtils{}
	
	/**
	 * 文件操作工具类
	 */
	public static class File extends FileUtils{}
	
	/**
	 * IO流操作工具类
	 */
	public static class IO extends IOUtils{}
	
	/**
	 * JSON、java对象序列化工具类
	 */
	public static class JSON extends JSONUtils{}
	
	/**
	 * List操作工具类
	 */
	public static class List extends ListUtils{}
	
	/**
	 * Map操作工具类
	 */
	public static class Map extends MapUtils{}
	
	/**
	 * 数值操作工具类
	 */
	public static class Number extends NumberUtils{}
	
	/**
	 * 基本对象工具类
	 */
	public static class Object extends ObjectUtils{}
	
	/**
	 * 反射工具类
	 */
	public static class Reflection extends ReflectionUtils{}
	
	/**
	 * 序列化工具类
	 */
	public static class Serialization extends SerializationUtils{}
	
	/**
	 * Set工具类
	 */
	public static class Set extends SetUtils{}
	
	/**
	 * 字符串处理工具类
	 */
	public static class String extends StringUtils{}
	
	/**
	 * 类型工具类
	 */
	public static class Type extends TypeUtils{}
	
	/**
	 * 异常处理类
	 */
	public static class Exception extends ExceptionUtils {}
	
}
