/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.gateway;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import micro.gateway.filter.GatewayAuthenticationFilter;
import micro.gateway.filter.GatewayErrorFilter;
import micro.gateway.filter.GatewayJSONTransformFilter;
import micro.web.config.WebMvcBasicProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;

/**
 * 网关自定义配置
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月11日
 */
@Configuration
@EnableConfigurationProperties(WebMvcBasicProperties.class)
public class GatewayConfigurer {
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@SuppressWarnings("rawtypes")
	@Autowired(required = false)
	private List<RibbonRequestCustomizer> requestCustomizers = Collections.emptyList();

//	/**
//	 * used for WebMvc converter
//	 * @return primary Jackson ObjectMapper with bean name 'jsonMapper'
//	 */
//	@Bean
//	@Primary
//	public ObjectMapper jsonMapper() {
//	    return JSONMapper.withApiConfig();
//	}
//	
//	/**
//	 * used for feign client
//	 * @return Jackson ObjectMapper with bean name 'objectMapper'
//	 */
//	@Bean
//	public ObjectMapper objectMapper() {
//		return JSONMapper.withSpiConfig();
//	}
	
	// pre filter
	@Bean
	public ZuulFilter gatewayAuthenticationFilter(){
		return new GatewayAuthenticationFilter();
	}

	// route filter
	/**
	 * override Bean generated by
	 * {@link org.springframework.cloud.netflix.zuul.ZuulProxyConfiguration#ribbonRoutingFilter(ProxyRequestHelper, RibbonCommandFactory)}
	 */
	@Bean
	public ZuulFilter ribbonRoutingFilter(ProxyRequestHelper helper,
			RibbonCommandFactory<?> ribbonCommandFactory) {
		GatewayErrorFilter filter = new GatewayErrorFilter(helper, ribbonCommandFactory, this.requestCustomizers);
		return filter;
	}

	// post filter
	@Bean
	public ZuulFilter gatewayJSONTransformFilter(ObjectMapper mapper){
		GatewayJSONTransformFilter filter = new GatewayJSONTransformFilter();
		filter.setApiMapper(webMvcBasicProperties.controllerMapper());
		filter.setSpiMapper(mapper);
		return filter;
	}
	
}