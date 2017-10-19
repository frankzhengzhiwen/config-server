/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.gateway.filter;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandContext;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.http.client.ClientHttpResponse;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 错误消息过滤器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月28日
 */
public class GatewayErrorFilter extends RibbonRoutingFilter {

	/**
	 * Creates a new instance of GatewayErrorFilter
	 * 
	 * @param helper
	 * @param ribbonCommandFactory
	 * @param requestCustomizers
	 */
	@SuppressWarnings("rawtypes")
	public GatewayErrorFilter(ProxyRequestHelper helper, RibbonCommandFactory<?> ribbonCommandFactory,
			List<RibbonRequestCustomizer> requestCustomizers) {
		super(helper, ribbonCommandFactory, requestCustomizers);
	}

	/**
	 * Creates a new instance of GatewayErrorFilter
	 * 
	 * @param ribbonCommandFactory
	 */
	public GatewayErrorFilter(RibbonCommandFactory<?> ribbonCommandFactory) {
		super(ribbonCommandFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		this.helper.addIgnoredHeaders();
		try {
			RibbonCommandContext commandContext = buildCommandContext(ctx);
			ClientHttpResponse response = forward(commandContext);
			setResponse(response);
			return response;
		}
		catch (ZuulException ex) {
			ctx.getResponse().setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			ctx.set("error.status_code", HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			ctx.set("error.exception", ex);
		}
		catch (Exception ex) {
			ctx.getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ctx.set("error.exception", ex);
		}
		return null;
	}

}
