/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import micro.core.tool.JSONMapper;
import micro.core.tool.Tools;
import micro.core.tool.extension.SpringExtensionLoader;
import micro.core.tool.extension.SpringExtensionRegistrar;
import micro.web.controller.BasicErrorController;
import micro.web.convert.DefaultWebBindingInitializer;
import micro.web.convert.DynamicMappingJackson2HttpMessageConverter;
import micro.web.extension.spi.WebMvcPreHandler;
import micro.web.interceptor.ControllerAdvice;
import micro.web.interceptor.ExceptionAdvice;

/**
 * WebMvc基础配置类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月11日
 */
@Configuration
@EnableConfigurationProperties(WebMvcBasicProperties.class)
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class WebMvcBasicAutoConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(WebMvcBasicAutoConfiguration.class);
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@Bean
	public ObjectMapper objectMapper(){
		return JSONMapper.withSpiConfig();
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		return new DynamicMappingJackson2HttpMessageConverter(webMvcBasicProperties.controllerMapper());
//		return new DynamicMappingJackson2HttpMessageConverter(objectMapper());
	}
	
	/**
	 * @return Spring容器扩展接口注册类
	 */
	@Bean
	public SpringExtensionRegistrar webMvcPreHandlerLoader(){
		return new SpringExtensionRegistrar();
	}
	
	/**
	 * WebMvc配置扩展
	 * 
	 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
	 * @version 0.0.1
	 * @date 2017年5月12日
	 */
	@Configuration
	@AutoConfigureBefore({WebMvcAutoConfiguration.class,ErrorMvcAutoConfiguration.class})
	public static class WebMvcConfigurer extends WebMvcConfigurerAdapter {
		
		@Autowired
		private SpringExtensionLoader springExtensionLoader;

		@Bean
		public BasicErrorController basicErrorController(){
			return new BasicErrorController();
		}
		
		@Bean
		public ExceptionAdvice exceptionAdvice(){
			return new ExceptionAdvice();
		}
		
		@Bean
		public ControllerAdvice controllerAdvice(){
			return new ControllerAdvice();
		}
		
		/**
		 * used for SpringMVC request with form/data
		 * @param requestMappingHandlerAdapter
		 */
		@Autowired
		public void setWebBindingInitializer(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
			requestMappingHandlerAdapter.setWebBindingInitializer(new DefaultWebBindingInitializer());
		}

		/**
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
		 */
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			
			List<WebMvcPreHandler> webMvcPreHandlers = springExtensionLoader.get(WebMvcPreHandler.class);
			if(Tools.Collection.isNotEmpty(webMvcPreHandlers)){
				for(WebMvcPreHandler preHandler : webMvcPreHandlers){
					
					HandlerInterceptorAdapter interceptorAdapter = new HandlerInterceptorAdapter(){

						/**
						 * {@inheritDoc}
						 */
						@Override
						public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
								throws Exception {
							return preHandler.preHandle(request, response, handler);
						}
						
					};
					registry.addInterceptor(interceptorAdapter).addPathPatterns("/**");
				}
			}
			super.addInterceptors(registry);
		}
		
		/**
		 * 跨域访问不控制，在请求时限制
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry)
		 */
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
			        .allowedHeaders("*")
			        .allowedMethods("*")
			        .allowedOrigins("*");
			super.addCorsMappings(registry);
		}

	}
	
//	/**
//	 * 不能重写HttpMessageConverter: feign客户端调用时会作为encoder，此时与返回请求客户端数据格式不一致
//	 * 
//	 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
//	 * @version 0.0.1
//	 * @date 2017年9月5日
//	 */
//	@Configuration
//	@EnableConfigurationProperties(WebMvcBasicProperties.class)
//	@Deprecated
//	public static class WebMvcReconfigurer implements InitializingBean {
//		
//		@Autowired
//		private WebMvcBasicProperties webMvcBasicProperties;
//		
//		private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
//		
//		private HttpMessageConverters httpMessageConverters;
//		
//		/**
//		 * Creates a new instance of WebMvcReconfigurer
//		 * @param requestMappingHandlerAdapter
//		 */
//		public WebMvcReconfigurer(RequestMappingHandlerAdapter requestMappingHandlerAdapter,
//					HttpMessageConverters httpMessageConverters) {
//			this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
//			this.httpMessageConverters = httpMessageConverters;
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		@Override
//		public void afterPropertiesSet() throws Exception {
//			logger.debug("RequestMappingHandlerAdapter---------------------------------");
//			requestMappingHandlerAdapter.getMessageConverters().forEach((converter)->{
//				logger.debug(converter.getClass().getName());
//				if(StringHttpMessageConverter.class.isAssignableFrom(converter.getClass())){
//					logger.debug("reset UTF-8 Charset in RequestMappingHandlerAdapter.StringHttpMessageConverter");
//					StringHttpMessageConverter stringConverter = (StringHttpMessageConverter) converter;
//					stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
//				} else if(MappingJackson2HttpMessageConverter.class.isAssignableFrom(converter.getClass())) {
//					logger.debug("reset ObjectMapper in RequestMappingHandlerAdapter.MappingJackson2HttpMessageConverter");
//					MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
//					jacksonConverter.setObjectMapper(webMvcBasicProperties.controllerMode());
//				}
//			});
//			
//			logger.debug("HttpMessageConverters---------------------------------");
//			httpMessageConverters.forEach((converter)->{
//				logger.debug(converter.getClass().getName());
//				if(StringHttpMessageConverter.class.isAssignableFrom(converter.getClass())){
//					logger.debug("reset UTF-8 Charset in HttpMessageConverters.StringHttpMessageConverter");
//					StringHttpMessageConverter stringConverter = (StringHttpMessageConverter) converter;
//					stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
//				} else if(MappingJackson2HttpMessageConverter.class.isAssignableFrom(converter.getClass())) {
//					logger.debug("reset ObjectMapper in HttpMessageConverters.MappingJackson2HttpMessageConverter");
//					MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
//					jacksonConverter.setObjectMapper(webMvcBasicProperties.controllerMode());
////					jacksonConverter.setObjectMapper(webMvcBasicProperties.rpcMode());
//				}
//			});
//		}
//		
//	}
}
