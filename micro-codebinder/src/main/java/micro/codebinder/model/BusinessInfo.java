/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.model;

import micro.codebinder.annotation.AccessLimit;

/**
 * 业务接口信息
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月7日
 */
public class BusinessInfo {

	/**
	 * 业务编码
	 */
	private String businessCode;
	
	/**
	 * 业务接口路径
	 */
	private String businessURI;
	
	/**
	 * 前置应用编码及场景编码组合成的完整业务编码
	 */
	private String fullCode;
	
	/**
	 * 权限控制类型
	 */
	private AccessLimit accessLimit;
	
	/**
	 * Creates a new instance of BusinessInfo
	 * 
	 * @param businessCode
	 * @param businessURI
	 */
	public BusinessInfo(String businessCode, String businessURI) {
		super();
		this.businessCode = businessCode;
		this.businessURI = businessURI;
	}

	/**
	 * @return property value of businessCode
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param businessCode value to be assigned to property businessCode
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * @return property value of businessURI
	 */
	public String getBusinessURI() {
		return businessURI;
	}

	/**
	 * @param businessURI value to be assigned to property businessURI
	 */
	public void setBusinessURI(String businessURI) {
		this.businessURI = businessURI;
	}

	/**
	 * @return property value of fullCode
	 */
	public String getFullCode() {
		return fullCode;
	}

	/**
	 * @param fullCode value to be assigned to property fullCode
	 */
	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	/**
	 * @return property value of accessLimit
	 */
	public AccessLimit getAccessLimit() {
		return accessLimit;
	}

	/**
	 * @param accessLimit value to be assigned to property accessLimit
	 */
	public void setAccessLimit(AccessLimit accessLimit) {
		this.accessLimit = accessLimit;
	}
}
