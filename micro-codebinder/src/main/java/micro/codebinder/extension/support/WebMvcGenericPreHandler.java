/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.extension.support;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import micro.codebinder.extension.spi.ApiPreHandler;
import micro.codebinder.extension.spi.SpiPreHandler;
import micro.codebinder.model.ApplicationInfo;
import micro.codebinder.model.BusinessInfo;
import micro.core.tool.Tools;
import micro.core.tool.extension.SpringExtensionLoader;
import micro.web.config.WebMvcBasicProperties;
import micro.web.extension.spi.WebMvcPreHandler;
import micro.web.extension.support.BusinessCodeHolder;

/**
 * 统一拦截器：不处理非业务接口请求
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月23日
 */
public class WebMvcGenericPreHandler implements WebMvcPreHandler, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(WebMvcGenericPreHandler.class);
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@Autowired
	private SpringExtensionLoader springExtensionLoader;
	
	private ApplicationInfo applicationInfo;
	
	private List<ApiPreHandler> apiPreHandlers;
	
	private List<SpiPreHandler> spiPreHandlers;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<ApplicationInfo> applicationInfos = springExtensionLoader.get(ApplicationInfo.class);
		if(Tools.Collection.isNotEmpty(applicationInfos)) {
			applicationInfo = applicationInfos.get(0);
		}
		apiPreHandlers = springExtensionLoader.get(ApiPreHandler.class);
		spiPreHandlers = springExtensionLoader.get(SpiPreHandler.class);
	}
	
	/**
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * 返回为false需自行处理response返回数据
	 * 因设置有异常处理机制，故需要失败处理时，直接抛出异常
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		Method method = HandlerMethod.class.cast(handler).getMethod();
		
		
		
		// 客户端请求：网关本地服务
		if(webMvcBasicProperties.apiMatches(method)) {
			BusinessInfo businessInfo = null;
			// TODO 需要按照权限设计改造
			if(applicationInfo != null) {
				businessInfo = applicationInfo.getBusinessInfo(method);
				if(businessInfo != null && Tools.String.isNotBlank(businessInfo.getFullCode())){
					BusinessCodeHolder.set(businessInfo.getFullCode());
				}
			}
			
			if(Tools.Collection.isNotEmpty(apiPreHandlers)){
				for(ApiPreHandler apiPreHandler : apiPreHandlers) {
					apiPreHandler.preHandle(businessInfo);
				}
			}
			
		// 服务端请求
		} else if(webMvcBasicProperties.spiMatches(method)) {
			if(Tools.Collection.isNotEmpty(spiPreHandlers)){
				for(SpiPreHandler spiPreHandler : spiPreHandlers) {
					spiPreHandler.preHandle();
				}
			}
		// TODO
		// 客户端请求：网关透传服务
		} else if(true) {
		
		}
		return true;
	}

}
