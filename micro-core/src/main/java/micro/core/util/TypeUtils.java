/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * 类型工具类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月1日
 */
public class TypeUtils extends org.apache.commons.lang3.reflect.TypeUtils {

	/**
	 * Gets the {@link java.lang.Class} object of the argument type.
	 * <p>
	 * If the type is an {@link java.lang.reflect.ParameterizedType}, then it
	 * returns its {@link java.lang.reflect.ParameterizedType#getRawType()}
	 * </p>
	 *
	 * @param type
	 *            The type
	 * @param <A>
	 *            type of class object expected
	 * @return The Class<A> object of the type
	 * @throws java.lang.RuntimeException
	 *             If the type is a {@link java.lang.reflect.TypeVariable}. In
	 *             such cases, it is impossible to obtain the Class object
	 */
	public static Class<?> getClass(Type type) {
		if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			Class<?> componentClass = getClass(componentType);
			if (componentClass != null) {
				return (Class<?>) Array.newInstance(componentClass, 0).getClass();
			} else
				throw new UnsupportedOperationException("Unknown class:" + type.getClass());
		} else if (type instanceof Class) {
			Class<?> claz = (Class<?>) type;
			return claz;
		} else if (type instanceof ParameterizedType) {
			return getClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof TypeVariable) {
			throw new RuntimeException(
					"The type signature is erased. The type class cant be known by using reflection");
		} else
			throw new UnsupportedOperationException("Unknown class:" + type.getClass());
	}
}
