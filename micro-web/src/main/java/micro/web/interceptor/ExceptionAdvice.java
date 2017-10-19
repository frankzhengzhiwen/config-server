/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.interceptor;

import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import micro.core.infrastructure.component.dto.ErrorInfo;
import micro.core.infrastructure.component.dto.JSONResult;
import micro.core.infrastructure.exception.AuthenticationException;
import micro.core.infrastructure.exception.AuthorizationException;
import micro.core.infrastructure.exception.BaseException;
import micro.core.infrastructure.exception.BusinessException;
import micro.core.infrastructure.exception.ParameterException;
import micro.core.infrastructure.exception.RemoteException;
import micro.core.infrastructure.exception.RepeatedException;
import micro.core.infrastructure.exception.SystemException;
import micro.core.infrastructure.exception.VersionException;
import micro.core.infrastructure.message.Message;
import micro.core.tool.Tools;
import micro.web.config.WebMvcBasicProperties;
import micro.web.constants.ConfigKey;
import micro.web.controller.BasicErrorController;
import micro.web.extension.support.BusinessCodeHolder;
import micro.web.tool.ObjectMapperHolder;
import micro.web.tool.PropertiesHolder;

/**
 * 微服务统一异常处理类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月22日
 */
@ControllerAdvice
public class ExceptionAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
	
	@Autowired
	private WebMvcBasicProperties webMvcBasicProperties;
	
	@Autowired
    private BasicErrorController errorController;
	
	private static Method METHOD;
	
	public static void main(String[] args) {
		System.out.println(((Object)"").getClass().getName());
	}
	
	static{
		try {
			METHOD = ExceptionAdvice.class.getMethod("responseErrorHandler",
					new Class<?>[]{HttpServletRequest.class, HttpServletResponse.class,
								HandlerMethod.class, Throwable.class});
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("init method in exceptionAdvice error.", e);
		}
	}
	
	public static boolean exceptionMethod(Method method){
		return ExceptionAdvice.METHOD == null ? false : ExceptionAdvice.METHOD.equals(method);
	}

	@ExceptionHandler({Throwable.class})
    @ResponseBody
    public Object responseErrorHandler(HttpServletRequest request,
    		HttpServletResponse response, HandlerMethod handlerMethod, Throwable throwable) throws Throwable {

		JSONResult<ErrorInfo> result = new JSONResult<>();
		
		if(webMvcBasicProperties.apiMatches(handlerMethod.getMethod())){
			ObjectMapperHolder.set(webMvcBasicProperties.controllerMapper());
		}
		
		String remoteServiceValue = request.getHeader(ConfigKey.REMOTE_SERVICE_KEY);
		if(ConfigKey.REMOTE_SERVICE_REQUEST.equals(remoteServiceValue)) {
			response.addHeader(ConfigKey.REMOTE_SERVICE_KEY, ConfigKey.REMOTE_SERVICE_RESPONSE_CUSTOM_ERROR);
		}
		
		String businessCode = BusinessCodeHolder.get();
		
		// fetch root Exception wraped by HystrixRuntimeException
		Throwable cause = throwable;
		for(Throwable  tmp = throwable; tmp != null; tmp = tmp.getCause()) {
			if(tmp instanceof BaseException || tmp instanceof RemoteException
					|| tmp instanceof SocketTimeoutException){
				cause = tmp;
				break;
			}
		}
		ErrorInfo errorInfo = defaultErrorInfo(request, cause);
		if(cause instanceof BaseException){
			BaseException e = BaseException.class.cast(cause);
			response.setStatus(Integer.parseInt(e.code()));
			result.setCode(Tools.String.defaultIfBlank(businessCode, e.code()));
			if(logger.isDebugEnabled())
				logger.debug("request business error", cause);
			if(cause instanceof BusinessException) {
				result.setMessage(
						PropertiesHolder.getProperty(Message.BUSINESS_ERROR,
								wrapBusinessCode(businessCode), e.getMessage()));
			}else if(cause instanceof ParameterException){
				result.setMessage(
						PropertiesHolder.getProperty(Message.PARAMETER_ERROR,
								wrapBusinessCode(businessCode), e.getMessage()));
			}else if(cause instanceof RepeatedException) {
				result.setMessage(
						PropertiesHolder.getProperty(Message.REPEATED_ERROR,
								wrapBusinessCode(businessCode)));
			}else if(cause instanceof SystemException){
				result.setMessage(
						PropertiesHolder.getProperty(Message.SYSTEM_ERROR,
								wrapBusinessCode(businessCode), e.getMessage()));
			}else if(cause instanceof AuthorizationException){
				result.setMessage(
						PropertiesHolder.getProperty(Message.UNAUTHORIZED, e.getMessage()));
			}else if(cause instanceof AuthenticationException){
				result.setMessage(
						PropertiesHolder.getProperty(Message.FORBIDDEN, e.getMessage()));
			}else if(cause instanceof VersionException){
				result.setMessage(
						PropertiesHolder.getProperty(Message.VERSION_ERROR,
								wrapBusinessCode(businessCode)));
			}
		} else if(cause instanceof RemoteException) {
			RemoteException e = RemoteException.class.cast(cause);
			response.setStatus(Integer.parseInt(e.code()));
			result.setCode(Tools.String.defaultIfBlank(businessCode, e.code()));
			result.setMessage(e.getMessage());
			if(e.errorInfo() != null)  {
				errorInfo = e.errorInfo();
				errorInfo.setRequestChain(request.getServletPath()+"==>"+errorInfo.getRequestChain());
			}
		} else if(cause instanceof HystrixRuntimeException) {
			response.setStatus(Message.SERVICE_UNAVAILABLE);
			result.setCode(Tools.String.defaultIfBlank(businessCode, String.valueOf(Message.SERVICE_UNAVAILABLE)));
			result.setMessage(PropertiesHolder.getProperty(Message.SERVICE_UNAVAILABLE, "不可用"));
		} else if(cause instanceof SocketTimeoutException) {
			response.setStatus(Message.SERVICE_UNAVAILABLE);
			result.setCode(Tools.String.defaultIfBlank(businessCode, String.valueOf(Message.SERVICE_UNAVAILABLE)));
			result.setMessage(PropertiesHolder.getProperty(Message.SERVICE_UNAVAILABLE, "超时"));
			errorInfo = defaultErrorInfo(request, throwable);
		} else {
//			response.setStatus(Message.INTERNAL_SERVER_ERROR);
//			result.setCode(String.valueOf(Message.INTERNAL_SERVER_ERROR));
//			result.setMessage(PropertiesHolder.getProperty(Message.INTERNAL_SERVER_ERROR));
//			errorInfo = defaultErrorInfo(request, throwable);
			throw throwable;
		}
		
		if(errorInfo != null && Tools.String.isBlank(errorInfo.getStatus())) {
			errorInfo.setStatus(String.valueOf(response.getStatus()));
		}
		result.setData(errorInfo);
			
		return result;
    }
	
	private String wrapBusinessCode(String businessCode) {
		return Tools.String.isBlank(businessCode) ? "" : "【"+businessCode+"】";
	}
	
	private ErrorInfo defaultErrorInfo(HttpServletRequest request, Throwable throwable) {
		if(errorController.isIncludeStackTrace(request, MediaType.ALL)){
			ErrorInfo errorInfo = new ErrorInfo();
			
    		errorInfo.setTimestamp(new Date());
    		errorInfo.setException(throwable.getClass().getName());
    		errorInfo.setPath(request.getServletPath());
    		errorInfo.setRequestChain(request.getServletPath());
    		errorInfo.setTrace(Tools.Exception.getStackTrace(throwable));
    		return errorInfo;
    	}
		return null;
	}
	
}
