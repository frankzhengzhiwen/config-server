/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.feign.codec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import micro.core.infrastructure.component.dto.ErrorInfo;
import micro.core.infrastructure.component.dto.JSONResult;
import micro.core.infrastructure.exception.RemoteException;
import micro.core.infrastructure.message.Message;
import micro.core.tool.Tools;
import micro.web.constants.ConfigKey;
import micro.web.tool.HttpStatusParser;
import micro.web.tool.PropertiesHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import feign.Response;

/**
 * FeignClient错误解码器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月3日
 */
public class ErrorDecoder implements feign.codec.ErrorDecoder {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorDecoder.class);
	
	private static final TypeFactory TYPE_FACTORY = TypeFactory.defaultInstance();
	
	private ObjectMapper mapper;

	/**
	 * Creates a new instance of ErrorDecoder
	 * 
	 * @param objectMapper
	 */
	public ErrorDecoder(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Exception decode(String methodKey, Response response) {
		
		String body = "";
		try {
			int status = response.status();
			if (response.body() == null){
				return new RemoteException(HttpStatusParser.parseHttpErrorStatus(status));
			}
			Reader reader = response.body().asReader();
			if (!reader.markSupported()) {
				reader = new BufferedReader(reader, 1);
			}
			reader.mark(1);
			if (reader.read() == -1) {
				return null; // Eagerly returning null avoids "No content to map
								// due to end-of-input"
			}
			reader.reset();
			body = Tools.IO.toString(reader);
			JSONResult<ErrorInfo> result = mapper.readValue(body,
					TYPE_FACTORY.constructParametricType(JSONResult.class, ErrorInfo.class));
			logger.debug("receive error from remote service: \n" + result);
			if(result == null){
				return new RemoteException("返回为空");
			}
			
			Collection<String> codes = response.headers().get(ConfigKey.REMOTE_SERVICE_KEY);
			String errorHeadCode = ConfigKey.HTTP_UNKNOWN;
			if(Tools.Collection.isNotEmpty(codes)){
				for(String s : codes){
					if(Tools.String.isNoneBlank(s)){
						errorHeadCode = s;
						break;
					}
				}
			}
			switch (errorHeadCode) {
				case ConfigKey.REMOTE_SERVICE_RESPONSE_OTHER_ERROR:
					String message = PropertiesHolder.getProperty(Message.REMOTE_ERROR, "【"+result.getCode()+"】");
					return new RemoteException(message)
							.withErrorInfo(result.getData())
							.withCode(response.status());
					
				case ConfigKey.REMOTE_SERVICE_RESPONSE_CUSTOM_ERROR:
					return new RemoteException(result.getMessage())
							.withErrorInfo(result.getData())
							.withCode(response.status());
					
				default:
					return new RemoteException("未知错误");
			}
		} catch (IOException e) {
			logger.error("can not handle remote response: " + body, e);
			// 可能服务不可用或网关错误时返回不能解析的字符串
			return new RemoteException("服务不可用", e);
		}
	}

}
