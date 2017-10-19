/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import micro.codebinder.enums.AccessType;

/**
 * 权限控制类型
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月7日
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

	AccessType value() default AccessType.Strict;
}