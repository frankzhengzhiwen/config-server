/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AspectJTypeFilter;

import com.google.common.collect.Lists;

import micro.codebinder.annotation.AccessLimit;
import micro.codebinder.annotation.BusinessCode;
import micro.codebinder.annotation.ScenarioCode;
import micro.codebinder.model.ApplicationInfo;
import micro.codebinder.model.BusinessInfo;
import micro.codebinder.model.ScenarioInfo;
import micro.core.tool.Tools;
import micro.core.tool.extension.SpringExtensionLoader;
import micro.web.config.WebMvcBasicAutoConfiguration;
import micro.web.config.WebMvcBasicProperties;

/**
 * 根据请求编码重新设置路径
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月29日
 */
@Configuration
//@EnableAutoConfiguration
@EnableConfigurationProperties(WebMvcBasicProperties.class)
@AutoConfigureBefore(WebMvcBasicAutoConfiguration.class)
public class ApplicationInfoConfigurer implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationInfoConfigurer.class);
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@Autowired
	private SpringExtensionLoader springExtensionLoader;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<ApplicationInfo> applicationInfos = springExtensionLoader.get(ApplicationInfo.class);
//		WebMvcProperties WebMvcProperties = WebMvcProperties();
		if(!webMvcBasicProperties.isApiIncluded()){
			return;
		}
		if(Tools.Collection.isEmpty(applicationInfos)) {
			return;
		}
		ApplicationInfo applicationInfo = applicationInfos.get(0);
		if(applicationInfo == null) {
			return;
		}
		
		// Don't pull default filters (@Component, etc.):
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AspectJTypeFilter(webMvcBasicProperties.getApiFilterExpression(), ApplicationInfoConfigurer.class.getClassLoader()));
		provider.addIncludeFilter(new AnnotationTypeFilter(ScenarioCode.class));
		for (BeanDefinition beanDef : provider.findCandidateComponents(webMvcBasicProperties.getApiBasePackage())) {
			Class<?> targetClazz = Class.forName(beanDef.getBeanClassName());
			reconfigure(targetClazz, applicationInfo);
		}
	}
	
	/**
	 * @param requestCodeMappings
	 * @param targetClazz
	 * @param applicationInfo 
	 */
	private Map<Method, ScenarioInfo> reconfigure(Class<?> targetClazz, ApplicationInfo applicationInfo) {
		Map<Method, ScenarioInfo> businessMethods = new HashMap<>();
		// update ScenarioCode
		ScenarioCode scenarioCode = targetClazz.getAnnotation(ScenarioCode.class);
		
		ScenarioInfo scenarioInfo = applicationInfo.getScenarioInfo(scenarioCode.value());
		if(scenarioInfo == null) {
			return businessMethods;
		}
		Tools.Annotation.changeAnnotationValue(scenarioCode, "path", new String[]{scenarioInfo.getScenarioURI()});
		logger.debug("find and reconfigure mapping annotation class: {} with new path'{}'",
				targetClazz.getName(), scenarioInfo.getScenarioURI());
		
		Tools.Reflection.doWithMethods(targetClazz,
			(method)->{
				// update HttpCode
				BusinessCode httpCode = Tools.Annotation.findAnnotation(method, BusinessCode.class);
				if(httpCode != null) {
					String businessCode = httpCode.value();
					String[] validBusinessCodes = validBusinessCodes(businessCode);
					List<BusinessInfo> businessInfos = scenarioInfo.getBusinessInfos();
					for(BusinessInfo businessInfo : businessInfos) {
						if(businessInfo.getBusinessCode().equals(validBusinessCodes[0])){
							String businessPath = businessInfo.getBusinessURI();
							if(Tools.String.isNoneBlank(businessPath)){
								businessPath = businessPath+validBusinessCodes[1];
								Tools.Annotation.changeAnnotationValue(httpCode, "path",
										new String[]{businessPath});
								logger.debug("find and reconfigure HttpCode annotation method: {} with new path'{}'",
										method.getName(), businessPath);
							}
							businessInfo.setFullCode(
									constructFullCode(applicationInfo.getAppCode(), scenarioCode.value(), validBusinessCodes[0]));
							
							AccessLimit accessLimit = Tools.Annotation.findAnnotation(method, AccessLimit.class);
							businessInfo.setAccessLimit(accessLimit);
							applicationInfo.cache(method, businessInfo);
						}
					}
				}
			}
		);
		return businessMethods;
	}
	
	/**
	 * 获取有效方法编码数组
	 * @param businessCode
	 * @return
	 */
	private static String[] validBusinessCodes(String businessCode) {
		String[] methodCodes = new String[2];
		if(Tools.String.isNoneBlank(businessCode)){
			int index = businessCode.indexOf("/{");
			if(index != -1) {
				methodCodes[0] = businessCode.substring(0, index);
				methodCodes[1] = businessCode.substring(index);
				return methodCodes;
			}
		}
		methodCodes[0] = businessCode;
		methodCodes[1] = "";
		
		return methodCodes;
	}
	
	/**
	 * 反射组合业务编码
	 * @param validBusinessCodes 
	 * @param handlerMethod
	 * @return
	 */
	private String constructFullCode(String appCode, String scenarioCode, String businessCode) {
		List<String> codeArray = Lists.newArrayList();
		codeArray.add(Tools.String.defaultIfBlank(appCode, ""));
		codeArray.add(Tools.String.defaultIfBlank(scenarioCode, ""));
		codeArray.add(Tools.String.defaultIfBlank(businessCode, ""));
		return Tools.String.join(codeArray.toArray(new String[]{}));
	}

}
