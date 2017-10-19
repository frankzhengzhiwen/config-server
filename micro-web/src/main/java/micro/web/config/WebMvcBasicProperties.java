/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.config;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fasterxml.jackson.databind.ObjectMapper;

import micro.core.tool.JSONMapper;
import micro.core.tool.Tools;

/**
 * Http请求仲裁者
 * 1. 是否对返回数据包装
 * 2. 是否进行权限校验
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月6日
 */
@ConfigurationProperties(prefix="webmvc.basic") // invalid
public class WebMvcBasicProperties implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(WebMvcBasicProperties.class);
	
	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();
	
	static {
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}
	
	// ==============================

	/**
	 * 客户端API调用类包路径
	 */
	private String apiBasePackage;
	
	/**
	 * 客户端API调用类过滤表达式
	 */
	private String apiFilterExpression;
	
	/**
	 * 是否包含客户端API调用类
	 */
	private boolean apiIncluded = false;
	
	/**
	 * 客户端API调用类AspectJ匹配表达式
	 */
	private PointcutExpression apiExpression;
	
	// ==============================
	
	/**
	 * 服务端SPI调用类包路径
	 */
	private String spiBasePackage;
	
	/**
	 * 服务端SPI调用类过滤表达式
	 */
	private String spiFilterExpression;
	
	/**
	 * 是否包含服务端SPI调用类
	 */
	private boolean spiIncluded = false;
	
	/**
	 * 服务端SPI调用类AspectJ匹配表达式
	 */
	private PointcutExpression spiExpression;
	
	// ==============================
	
	/**
	 * 请求响应模式：包含zuul、http、rpc请求响应
	 */
	private String controllerMode;
	
	/**
	 * 请求日期格式，缺省为默认格式yyyy-MM-dd HH:mm:ss
	 */
	private String controllerDateformat;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if(Tools.String.isNotBlank(apiBasePackage) && Tools.String.isNotBlank(apiFilterExpression)){
			apiIncluded = true;
			PointcutParser parser = PointcutParser
					.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
							SUPPORTED_PRIMITIVES, WebMvcBasicProperties.class.getClassLoader());
			apiExpression = parser.parsePointcutExpression(apiFilterExpression);
		}
		if(Tools.String.isNotBlank(spiBasePackage) && Tools.String.isNotBlank(spiFilterExpression)){
			spiIncluded = true;
			PointcutParser parser = PointcutParser
					.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
							SUPPORTED_PRIMITIVES, WebMvcBasicProperties.class.getClassLoader());
			spiExpression = parser.parsePointcutExpression(spiFilterExpression);
		}
	}
	
	public boolean apiMatches(Method method) {
		if(!apiIncluded){
			return false;
		}
		Package pkg = method.getDeclaringClass().getPackage();
		if(pkg == null || Tools.String.isBlank(pkg.getName())) {
			return false;
		}
		ShadowMatch shadowMatch = apiExpression.matchesMethodExecution(method);
		return pkg.getName().startsWith(apiBasePackage) && shadowMatch.alwaysMatches();
	}
	
	public boolean spiMatches(Method method) {
		if(!spiIncluded){
			return false;
		}
		Package pkg = method.getDeclaringClass().getPackage();
		if(pkg == null || Tools.String.isBlank(pkg.getName())) {
			return false;
		}
		ShadowMatch shadowMatch = spiExpression.matchesMethodExecution(method);
		return pkg.getName().startsWith(spiBasePackage) && shadowMatch.alwaysMatches();
	}
	
	/**
	 * @return 请求响应模式JSON解析类
	 */
	public ObjectMapper controllerMapper() {
		if("api".equalsIgnoreCase(controllerMode)) {
			return JSONMapper.withApiConfig(controllerDateformat);
		}
		return JSONMapper.withSpiConfig();
	}
	
	/**
	 * @return property value of apiBasePackage
	 */
	public String getApiBasePackage() {
		return apiBasePackage;
	}

	/**
	 * @param apiBasePackage value to be assigned to property apiBasePackage
	 */
	public void setApiBasePackage(String apiBasePackage) {
		this.apiBasePackage = apiBasePackage;
	}

	/**
	 * @return property value of apiFilterExpression
	 */
	public String getApiFilterExpression() {
		return apiFilterExpression;
	}

	/**
	 * @param apiFilterExpression value to be assigned to property apiFilterExpression
	 */
	public void setApiFilterExpression(String apiFilterExpression) {
		this.apiFilterExpression = apiFilterExpression;
	}

	/**
	 * @return property value of apiIncluded
	 */
	public boolean isApiIncluded() {
		return apiIncluded;
	}

	/**
	 * @param apiIncluded value to be assigned to property apiIncluded
	 */
	public void setApiIncluded(boolean apiIncluded) {
		this.apiIncluded = apiIncluded;
	}

	/**
	 * @return property value of apiExpression
	 */
	public PointcutExpression getApiExpression() {
		return apiExpression;
	}

	/**
	 * @param apiExpression value to be assigned to property apiExpression
	 */
	public void setApiExpression(PointcutExpression apiExpression) {
		this.apiExpression = apiExpression;
	}

	/**
	 * @return property value of spiBasePackage
	 */
	public String getSpiBasePackage() {
		return spiBasePackage;
	}

	/**
	 * @param spiBasePackage value to be assigned to property spiBasePackage
	 */
	public void setSpiBasePackage(String spiBasePackage) {
		this.spiBasePackage = spiBasePackage;
	}

	/**
	 * @return property value of spiFilterExpression
	 */
	public String getSpiFilterExpression() {
		return spiFilterExpression;
	}

	/**
	 * @param spiFilterExpression value to be assigned to property spiFilterExpression
	 */
	public void setSpiFilterExpression(String spiFilterExpression) {
		this.spiFilterExpression = spiFilterExpression;
	}

	/**
	 * @return property value of spiIncluded
	 */
	public boolean isSpiIncluded() {
		return spiIncluded;
	}

	/**
	 * @param spiIncluded value to be assigned to property spiIncluded
	 */
	public void setSpiIncluded(boolean spiIncluded) {
		this.spiIncluded = spiIncluded;
	}

	/**
	 * @return property value of spiExpression
	 */
	public PointcutExpression getSpiExpression() {
		return spiExpression;
	}

	/**
	 * @param spiExpression value to be assigned to property spiExpression
	 */
	public void setSpiExpression(PointcutExpression spiExpression) {
		this.spiExpression = spiExpression;
	}

	/**
	 * @return property value of controllerMode
	 */
	public String getControllerMode() {
		return controllerMode;
	}

	/**
	 * @param controllerMode value to be assigned to property controllerMode
	 */
	public void setControllerMode(String controllerMode) {
		this.controllerMode = controllerMode;
	}

	/**
	 * @return property value of controllerDateformat
	 */
	public String getControllerDateformat() {
		return controllerDateformat;
	}

	/**
	 * @param controllerDateformat value to be assigned to property controllerDateformat
	 */
	public void setControllerDateformat(String controllerDateformat) {
		this.controllerDateformat = controllerDateformat;
	}
	
}
