/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

import micro.core.tool.Tools;

/**
 * 通用断言类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月16日
 */
public class Assert extends org.springframework.util.Assert {
	
	/**
	 * 断定字符串相等
	 * @param first
	 * @param second
	 * @param message
	 */
	public static void stringEquals(String first, String second, String message) {
		boolean equals = false;
		if(first == null){
			if(second == null){
				equals = true;
			}
		}else{
			equals = first.equals(second);
		}
		if(!equals){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断定字符串不相等
	 * @param first
	 * @param second
	 * @param message
	 */
	public static void stringNotEquals(String first, String second, String message) {
		boolean equals = false;
		if(first == null){
			if(second == null){
				equals = true;
			}
		}else{
			equals = first.equals(second);
		}
		if(equals){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断定对象相等
	 * @param first
	 * @param second
	 * @param message
	 */
	public static void notEquals(Object first, Object second, String message) {
		if(EqualsBuilder.reflectionEquals(first, second)){
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断定对象相等
	 * @param first
	 * @param second
	 * @param message
	 */
	public static void equals(Object first, Object second, String message) {
		if(!EqualsBuilder.reflectionEquals(first, second)){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断定字符串对象不为空
	 * @param list
	 * @param message
	 */
	public static void hasTextString(Object obj, String message) {
		if(obj == null || Tools.String.isBlank(obj.toString())){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断定字符串列表不能包含空字符串
	 * @param list
	 * @param message
	 */
	public static void noneBlackString(List<String> list, String message) {
		if(Tools.Collection.isNotEmpty(list)){
			for(String s : list){
				hasText(s, "List can not has black String: "+list);
			}
		}
	}
	
	/**
	 * 断定字符串数组不能包含空字符串
	 * @param array
	 * @param message
	 */
	public static void noneBlackString(String[] array, String message) {
		if(Tools.Array.isNotEmpty(array)){
			noneBlackString(Arrays.asList(array), message);
		}
	}
	
	/**
	 * 断定列表必须包含元素
	 * @param collection
	 * @param element
	 */
	public static <T> void contains(Collection<T> collection, T element) {
		if(!collection.contains(collection)) {
			throw new IllegalArgumentException("Collection must contains the element: " + element);
		}
	}

	/**
	 * 断定列表不能包含元素
	 * @param collection
	 * @param element
	 */
	public static <T> void doesNotContain(Collection<T> collection, T element) {
		if(collection.contains(collection)) {
			throw new IllegalArgumentException("Collection can not contains the same element: " + element);
		}
	}
}
