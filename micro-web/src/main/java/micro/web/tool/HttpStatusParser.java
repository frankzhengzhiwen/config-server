/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import micro.core.infrastructure.message.Message;

/**
 * Http状态码错误消息解析类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月16日
 */
public class HttpStatusParser {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpStatusParser.class);

	/**
	 * 解析Http code，不为2xx的状态码
	 * @param status
	 * @return
	 */
	public static java.lang.String parseHttpErrorStatus(int status) {
		try {
			HttpStatus httpStatus = HttpStatus.valueOf(status);
			if(httpStatus.is1xxInformational()){
				return "临时响应，需要请求者继续执行操作的状态代码";
			}else if(httpStatus.is3xxRedirection()){
				return "重定向，要完成请求，需要进一步操作";
			}else if(HttpStatus.UNAUTHORIZED.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "未经授权的请求");
			}else if(HttpStatus.FORBIDDEN.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "权限验证失败");
			}else if(HttpStatus.NOT_FOUND.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "资源不存在");
			}else if(HttpStatus.METHOD_NOT_ALLOWED.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "不允许的请求方法");
			}else if(HttpStatus.NOT_ACCEPTABLE.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "不接受的MIME类型");
			}else if(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "代理服务授权失败");
			}else if(HttpStatus.REQUEST_TIMEOUT.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "超时");
			}else if(HttpStatus.PAYLOAD_TOO_LARGE.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "请求参数或上传数据太大");
			}else if(HttpStatus.URI_TOO_LONG.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "URI地址超长");
			}else if(HttpStatus.UNSUPPORTED_MEDIA_TYPE.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.BAD_REQUEST, "不支持的请求格式");
			}else if(httpStatus.is4xxClientError()){
				return "请求参数或类型设置错误";
			}else if(HttpStatus.BAD_GATEWAY.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.SERVICE_UNAVAILABLE, "网关不可用");
			}else if(HttpStatus.SERVICE_UNAVAILABLE.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.SERVICE_UNAVAILABLE, "不可用");
			}else if(HttpStatus.GATEWAY_TIMEOUT.equals(httpStatus)) {
				return PropertiesHolder.getProperty(Message.SERVICE_UNAVAILABLE, "网关超时");
			}else if(httpStatus.is5xxServerError()) {
				return "服务器内部错误";
			}
		} catch (Exception e) {
			logger.error("error response status with: " + status);
		}
    	return "未知错误";
	}
}
