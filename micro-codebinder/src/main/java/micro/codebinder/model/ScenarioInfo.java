/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景用例信息
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月17日
 */
public class ScenarioInfo {
	
	/**
	 * 场景编码
	 */
	private String scenarioCode;
	
	/**
	 * 场景路径
	 */
	private String scenarioURI;
	
	/**
	 * 场景业务接口信息
	 */
	private List<BusinessInfo> businessInfos = new ArrayList<>();
	
	/**
	 * 添加场景业务信息
	 * @param businessInfo
	 */
	public void add(BusinessInfo businessInfo){
		businessInfos.add(businessInfo);
	}
	
	/**
	 * 添加场景业务信息
	 * @param businessInfo
	 */
	public void add(List<BusinessInfo> businessInfos){
		this.businessInfos.addAll(businessInfos);
	}

	/**
	 * @return property value of scenarioCode
	 */
	public String getScenarioCode() {
		return scenarioCode;
	}

	/**
	 * @param scenarioCode value to be assigned to property scenarioCode
	 */
	public void setScenarioCode(String scenarioCode) {
		this.scenarioCode = scenarioCode;
	}

	/**
	 * @return property value of scenarioURI
	 */
	public String getScenarioURI() {
		return scenarioURI;
	}

	/**
	 * @param scenarioURI value to be assigned to property scenarioURI
	 */
	public void setScenarioURI(String scenarioURI) {
		this.scenarioURI = scenarioURI;
	}

	/**
	 * @return property value of businessInfos
	 */
	public List<BusinessInfo> getBusinessInfos() {
		return businessInfos;
	}

}
