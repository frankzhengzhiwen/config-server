/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.interceptor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import micro.core.extension.spi.HolderClearer;
import micro.core.infrastructure.component.dto.JSONResult;
import micro.core.infrastructure.message.Message;
import micro.core.tool.Tools;
import micro.core.tool.extension.SpringExtensionLoader;
import micro.web.config.WebMvcBasicProperties;
import micro.web.extension.spi.ApiResultHandler;
import micro.web.tool.PropertiesHolder;

/**
 * 微服务统一返回json格式处理
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月22日
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice implements InitializingBean, ResponseBodyAdvice<Object> {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	
	private static final String SUCCESS_MESSAGE = PropertiesHolder.getProperty(Message.SUCCESS);
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@Autowired
	private SpringExtensionLoader springExtensionLoader;
	
	private ApiResultHandler apiResultHandler;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<ApiResultHandler> apiResultHandlers = springExtensionLoader.get(ApiResultHandler.class);
		if(Tools.Collection.isNotEmpty(apiResultHandlers)){
			apiResultHandler = apiResultHandlers.get(0);
		}
	}
	
	/**
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#supports(org.springframework.core.MethodParameter, java.lang.Class)
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#beforeBodyWrite(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.http.MediaType, java.lang.Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		logger.debug("-----------------------------"+returnType.getMethod().getDeclaringClass().getPackage().getName());
		
		boolean apiMatches = webMvcBasicProperties.apiMatches(returnType.getMethod());
		boolean spiMatches = webMvcBasicProperties.spiMatches(returnType.getMethod());
		boolean exceptionMatches = ExceptionAdvice.exceptionMethod(returnType.getMethod());
		if(!apiMatches && !spiMatches && !exceptionMatches){
			return body;
		}
		JSONResult<Object> result = null;
		try {
			if(JSONResult.class.isAssignableFrom(body.getClass())) {
				result = (JSONResult<Object>) body;
			} else {
					result = new JSONResult<>();
					result.setSuccess(true);
					result.setMessage(SUCCESS_MESSAGE);
					result.setData(body);
			}
		} finally{
			HolderClearer.clearAll();
		}
		if(apiMatches && apiResultHandler != null){
			return apiResultHandler.handle(result);
		}
        return result;
	}
}
