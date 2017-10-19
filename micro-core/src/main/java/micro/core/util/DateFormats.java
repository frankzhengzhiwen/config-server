/**
 * Copyright (c) 2016, micro All Rights Reserved.
 */

package micro.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import micro.core.tool.Tools;

/**
 * 日期解析工具类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2015-8-11 下午1:23:34
 */
public class DateFormats {
	
	private static final Map<String, DateFormat> DATE_FORMATS = new HashMap<>();
	
	/**
	 * 解析日期字符串
	 * @param dateString
	 * @param datePattern
	 * @return
	 */
	public static Date parse(String dateString) {
		if(Tools.String.isBlank(dateString))
			return null;
		for(Entry<String, DateFormat> entry : DATE_FORMATS.entrySet()){
			DateFormat df = entry.getValue();
			if(df.matchDateString(dateString)){
				try {
					return df.getDf().parse(dateString);
				} catch (ParseException e) {
					throw new IllegalStateException("parse date '" + dateString + "' error", e);
				}
			}
		}
		throw new IllegalArgumentException("can not match registed date with '" + dateString + "'");
	}
	
	/**
	 * 日期格式化
	 * @param dateString
	 * @param datePattern
	 * @return
	 */
	public static String format(Date date, String datePattern) {
		return match(datePattern).format(date);
	}
	
	/**
	 * 获取指定日期格式的日期工具
	 * @param datePattern
	 * @return
	 */
	public static java.text.DateFormat match(String datePattern) {
		Assert.hasText(datePattern, "cannot match date pattern with null value.");
		DateFormat df = DATE_FORMATS.get(datePattern);
		if(df == null) {
			df = new DateFormat(datePattern);
			DATE_FORMATS.put(datePattern, df);
		}
		return df.getDf();
	}
	
	private static class DateFormat{
		
		private String regex;
		
		private java.text.DateFormat df;
		
		/**
		 * Creates a new instance of DateFormat
		 * 
		 * @param pattern
		 * @param regex
		 * @param df
		 */
		public DateFormat(String pattern) {
			super();
			this.regex = pattern
					.replaceAll("yyyy", "[0-9]{4}")
					.replaceAll("MM", "[0-1][0-9]")
					.replaceAll("dd", "[0-3][0-9]")
					.replaceAll("HH", "[0-5][0-9]")
					.replaceAll("mm", "[0-5][0-9]")
					.replaceAll("ss", "[0-5][0-9]")
					.replaceAll("SSS", "[0-9]{3}");
			this.df = new SimpleDateFormat(pattern);
		}
		
		/**
		 * 匹配日期常量
		 * @param dateString eg.2016-01-02 23:11:22
		 * @return boolean
		 */
		public boolean matchDateString(String dateString){
			
			Assert.hasText(dateString, "cannot match date regex with null value.");
			return dateString.matches(regex);
		}
		
		/**
		 * @return property value of df
		 */
		public java.text.DateFormat getDf() {
			return df;
		}
	}
	
}
