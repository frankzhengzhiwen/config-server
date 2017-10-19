/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.feign.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import micro.feign.codec.FeignSpringFormEncoder;
import micro.feign.codec.JSONResultDecoder;
import micro.web.config.WebMvcBasicProperties;
import micro.web.constants.ConfigKey;
//import micro.web.config.mapping.RequestCodeMappings;
import micro.web.controller.BasicErrorController;
import feign.Contract;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;

/**
 * Feign客户端调用配置
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月13日
 */
@Configuration
@EnableConfigurationProperties(WebMvcBasicProperties.class)
public class FeignConfigurer {
	
	private static final Logger logger = LoggerFactory.getLogger(FeignConfigurer.class);
	
//	private static final RequestCodeMappings requestCodeMappings = (RequestCodeMappings) PropertiesHolder.get(ConfigKey.APPLICATION_KEY);
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@Autowired
    private BasicErrorController errorController;
	
	@Bean
	@Primary
	@Scope("prototype")
	public RequestInterceptor requestInterceptor(HttpServletRequest request){
//		RibbonClientConfiguration
//		ILoadBalancer
//		IRule
//		IPing
//		ServerList
//		ServerListFilter
//		RoundRobinRule
//		DynamicServerListLoadBalancer
		return new RequestInterceptor(){

			@Override
			public void apply(RequestTemplate template) {
				// do not set Content-Type, feign would auto set and also append boundary with it 
//	            template.header(ConfigKey.REMOTE_OAUTH_HEADER_KEY, MediaType.MULTIPART_FORM_DATA_VALUE);
	            template.header(ConfigKey.REMOTE_SERVICE_KEY, ConfigKey.REMOTE_SERVICE_REQUEST);
	            if(errorController.isIncludeStackTrace(request, MediaType.ALL)){
	            	template.header(ConfigKey.REMOTE_TRACE_KEY, "true");
	            }
				// 系统级权限设置 TODO
				System.out.println("trace========================="+request.getParameter("trace"));
			}
		};
	}
	
	@Bean
	@Primary
	@Scope("prototype")
	public Contract feignContract(){
		return new Contract.Default();
	}

	@Bean
	@Primary
	@Scope("prototype")
	public Encoder feignEncoder(HttpMessageConverters httpMessageConverters){
		return new FeignSpringFormEncoder(httpMessageConverters);
	}
	
	@Bean
	@Primary
	@Scope("prototype")
	public Decoder feignDecoder(ObjectMapper mapper){
		return new JSONResultDecoder(mapper);
	}
	
	@Bean
	@Primary
	@Scope("prototype")
	public ErrorDecoder feignErrorDecoder(ObjectMapper mapper){
		return new micro.feign.codec.ErrorDecoder(mapper);
	}
	
}
