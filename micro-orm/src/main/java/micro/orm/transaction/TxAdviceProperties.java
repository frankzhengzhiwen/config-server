/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.orm.transaction;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 事务处理配置项
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月13日
 */
@ConfigurationProperties(prefix = "aop.transaction")
public class TxAdviceProperties {
	
	private String advisorPointCut;
	
	private Properties attributes;

	/**
	 * Creates a new instance of TxAdviceProperties
	 */
	public TxAdviceProperties() {
		super();
	}

	/**
	 * @return property value of advisorPointCut
	 */
	public String getAdvisorPointCut() {
		return advisorPointCut;
	}

	/**
	 * @param advisorPointCut value to be assigned to property advisorPointCut
	 */
	public void setAdvisorPointCut(String advisorPointCut) {
		this.advisorPointCut = advisorPointCut;
	}

	/**
	 * @return property value of attributes
	 */
	public Properties getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes value to be assigned to property attributes
	 */
	public void setAttributes(Properties attributes) {
		this.attributes = attributes;
	}
}
