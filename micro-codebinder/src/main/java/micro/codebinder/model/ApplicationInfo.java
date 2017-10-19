/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 应用信息
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月17日
 */
public class ApplicationInfo {
	
	/**
	 * 应用名称：对应sping.application.name
	 */
	private String appName;
	
	/**
	 * 应用编码
	 */
	private String appCode;
	
	/**
	 * 场景用例信息：key为编码，value为用例信息
	 */
	private Map<String, ScenarioInfo> scenarioInfos = new HashMap<>();
	
	/**
	 * 缓存本地调用方法及对应的业务信息
	 */
	@JsonIgnore
	private Map<Method, BusinessInfo> cache = new HashMap<>();

	/**
	 * @param scenarioCode: 用例编码
	 * @return 用例信息
	 */
	public ScenarioInfo getScenarioInfo(String scenarioCode){
		return scenarioInfos.get(scenarioCode);
	}
	
	/**
	 * 获取业务信息
	 * @param method
	 * @return
	 */
	public BusinessInfo getBusinessInfo(Method method) {
		return cache.get(method);
	}
	
	/**
	 * 保存用例信息
	 * @param scenarioCode
	 * @param scenarioInfo
	 */
	public void put(String scenarioCode, ScenarioInfo scenarioInfo) {
		scenarioInfos.put(scenarioCode, scenarioInfo);
	}
	
	/**
	 * 保存用例信息
	 * @param scenarioCode
	 * @param scenarioInfo
	 */
	public void put(Map<String, ScenarioInfo> scenarioInfos) {
		this.scenarioInfos.putAll(scenarioInfos);
	}
	
	/**
	 * 保存本地方法及业务信息
	 * @param method
	 * @param businessInfo
	 */
	public void cache(Method method, BusinessInfo businessInfo){
		cache.put(method, businessInfo);
	}
	
	/**
	 * 保存本地方法及业务信息
	 * @param method
	 * @param businessInfo
	 */
	public void cache(Map<Method, BusinessInfo> businessInfos){
		cache.putAll(businessInfos);
	}
	
	/**
	 * @return property value of appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName value to be assigned to property appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return property value of appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode value to be assigned to property appCode
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return property value of scenarioInfos
	 */
	public Map<String, ScenarioInfo> getScenarioInfos() {
		return scenarioInfos;
	}
}
