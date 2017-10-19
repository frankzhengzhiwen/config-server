/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller Http方法编码注解
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月30日
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface BusinessCode {
	
	/**
	 * @return 编码值
	 */
	String value() default "";
	
	/**
	 * Alias for {@link RequestMapping#method()}.
	 */
	@AliasFor(annotation = RequestMapping.class, attribute="method")
	RequestMethod[] method() default {};

	/**
	 * Alias for {@link RequestMapping#value}.
	 */
	@AliasFor(annotation = RequestMapping.class, attribute="value")
	String[] path() default {};
}
