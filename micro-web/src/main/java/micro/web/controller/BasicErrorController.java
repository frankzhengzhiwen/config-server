/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import micro.core.extension.spi.HolderClearer;
import micro.core.infrastructure.component.dto.JSONResult;
import micro.core.tool.Tools;
import micro.web.constants.ConfigKey;
import micro.web.extension.support.BusinessCodeHolder;
import micro.web.tool.HttpStatusParser;

/**
 * 自定义错误处理类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月24日
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController implements ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(BasicErrorController.class);
	
    @Autowired
    private ErrorAttributes errorAttributes;
    
    @Autowired
    private ServerProperties serverProperties;

    @RequestMapping
	@ResponseBody
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring. 
        // Here we just define response body.
    	JSONResult<Map<String, Object>> result = new JSONResult<>();
    	
    	try {
			String remoteServiceValue = request.getHeader(ConfigKey.REMOTE_SERVICE_KEY);
			if(ConfigKey.REMOTE_SERVICE_REQUEST.equals(remoteServiceValue)) {
				response.addHeader(ConfigKey.REMOTE_SERVICE_KEY, ConfigKey.REMOTE_SERVICE_RESPONSE_OTHER_ERROR);
			}
			int status = response.getStatus();
			String businessCode = BusinessCodeHolder.get();
			result.setCode(Tools.String.defaultIfBlank(businessCode, String.valueOf(status)));
			result.setMessage(HttpStatusParser.parseHttpErrorStatus(status));
			Map<String, Object> errorAtributes = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
			errorAtributes.put("requestChain", request.getServletPath());
			result.setData(errorAtributes);
		}  finally{
			HolderClearer.clearAll();
		}
    	return result;
    }

    /**
	 * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {
		return serverProperties.getError().getPath();
	}

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
    		boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
    
    /**
	 * Determine if the stacktrace attribute should be included.
	 * @param request the source request
	 * @param produces the media type produced (or {@code MediaType.ALL})
	 * @return if the stacktrace attribute should be included
	 */
    public boolean isIncludeStackTrace(HttpServletRequest request,
			MediaType produces) {
		IncludeStacktrace include = serverProperties.getError().getIncludeStacktrace();
		if (include == IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request) || getTraceHeader(request);
		}
		return false;
	}
    
    private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}
    
    private boolean getTraceHeader(HttpServletRequest request) {
    	String trace = request.getHeader(ConfigKey.REMOTE_TRACE_KEY);
    	if (trace == null) {
    		return false;
    	}
    	return !"false".equals(trace.toLowerCase());
    }

}
