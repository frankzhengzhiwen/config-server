/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul网关授权验证过滤器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月27日
 */
public class GatewayAuthenticationFilter extends ZuulFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * {@inheritDoc}
	 * before SendForwardFilter
	 */
	@Override
	public int filterOrder() {
		return -100;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		logger.debug("**********************************"+ctx.getRequest().getServletPath());
		return null;
	}
	
}
