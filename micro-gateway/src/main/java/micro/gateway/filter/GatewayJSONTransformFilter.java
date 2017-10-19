/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.gateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;

import micro.core.tool.Tools;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul透传JSON filter
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月27日
 */
public class GatewayJSONTransformFilter extends ZuulFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(GatewayJSONTransformFilter.class);
	
	private ObjectMapper apiMapper;
	
	private ObjectMapper spiMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		List<Pair<String, String>> headers = ctx.getZuulResponseHeaders();
		if(Tools.Collection.isNotEmpty(headers)){
			for(Pair<String, String> header : headers) {
				if("content-type".equalsIgnoreCase(header.first())){
					String contentType = header.second();
					if(Tools.String.isNotBlank(contentType) &&
							contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @EnableZuulServer: Filters Creates a SimpleRouteLocator that loads route definitions
	 * 		from Spring Boot configuration files.
	 * @see Pre filters: 	ServletDetectionFilter(-3)/FormBodyWrapperFilter(-1)/DebugFilter(1)
	 * @see Route filters:	SendForwardFilter(500)
	 * @see Post filters:	SendResponseFilter(1000)/SendErrorFilter(0)
	 * 
	 * @EnableZuulProxy is a superset of @EnableZuulServer.
	 * 		In other words, @EnableZuulProxy contains all filters installed by @EnableZuulServer. 
	 * @EnableZuulProxy: Filters Creates a DiscoveryClientRouteLocator that loads route definitions
	 * 		from a DiscoveryClient (like Eureka), as well as from properties.
	 * @see Pre filters:	PreDecorationFilter(5)
	 * @see Route filters:	RibbonRoutingFilter(10)/SimpleHostRoutingFilter(100)
	 * 
	 * 类型		顺序		过滤器						功能
	 * pre		-3		ServletDetectionFilter		标记处理Servlet的类型
	 * pre		-2		Servlet30WrapperFilter		包装HttpServletRequest请求
	 * pre		-1		FormBodyWrapperFilter		包装请求体
	 * route	1		DebugFilter					标记调试标志
	 * route	5		PreDecorationFilter			处理请求上下文供后续使用
	 * route	10		RibbonRoutingFilter			serviceId请求转发
	 * route	100		SimpleHostRoutingFilter		url请求转发
	 * route	500		SendForwardFilter			forward请求转发
	 * post		0		SendErrorFilter				处理有错误的请求响应
	 * post		1000	SendResponseFilter			处理正常的请求响应
	 */
	@Override
	public String filterType() {
		return "post";
	}

	/**
	 * {@inheritDoc}
	 * between SendForwardFilter and SendResponseFilter
	 */
	@Override
	public int filterOrder() {
		return 800;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object run() {
		try {
			writeResponse();
		}
		catch (Exception ex) {
			ReflectionUtils.rethrowRuntimeException(ex);
		}
		return null;
	}
	
	private void writeResponse() throws Exception {
		RequestContext ctx = RequestContext.getCurrentContext();
		// there is no body to send
		if (ctx.getResponseBody() == null
				&& ctx.getResponseDataStream() == null) {
			return;
		}
		HttpServletResponse servletResponse = ctx.getResponse();
		if (servletResponse.getCharacterEncoding() == null) { // only set if not set
			servletResponse.setCharacterEncoding("UTF-8");
		}
		OutputStream outStream = servletResponse.getOutputStream();
		InputStream is = null;
		
		try{
			String body = RequestContext.getCurrentContext().getResponseBody();
			if (Tools.String.isBlank(body)) {
				is = ctx.getResponseDataStream();
				InputStream inputStream = is;
				if (is != null) {
					if (ctx.sendZuulResponse()) {
						body = Tools.IO.toString(inputStream, Charset.forName(servletResponse.getCharacterEncoding()));
					}
				}
			}
			if (Tools.String.isNoneBlank(body)) {
				writeResponse(
						clientResponseJSON(body),
						outStream,
						Charset.forName(servletResponse.getCharacterEncoding()));
			}
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
				outStream.flush();
				// The container will close the stream for us
			}
			catch (IOException ex) {
			}
		}
		return;
	}
	
	private String clientResponseJSON(String json) throws IOException {
		Map<String, Object> result = spiMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		return apiMapper.writeValueAsString(result);
	}

	private void writeResponse(String body, OutputStream out, Charset charset) throws Exception {
		try {
			Tools.IO.write(body, out, charset);
		}
		catch(IOException ioe) {
			logger.warn("Error while sending response to client: "+ioe.getMessage());
		}
	}

	/**
	 * @param apiMapper value to be assigned to property apiMapper
	 */
	public void setApiMapper(ObjectMapper apiMapper) {
		this.apiMapper = apiMapper;
	}

	/**
	 * @param spiMapper value to be assigned to property spiMapper
	 */
	public void setSpiMapper(ObjectMapper spiMapper) {
		this.spiMapper = spiMapper;
	}
	
}
