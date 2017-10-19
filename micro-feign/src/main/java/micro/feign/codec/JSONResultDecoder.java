/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.feign.codec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import micro.core.infrastructure.component.dto.JSONResult;
import micro.core.infrastructure.exception.RemoteException;
import micro.core.tool.Tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;

import feign.Response;
import feign.codec.Decoder;

/**
 * 统一feign远程调用JSON解码器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月25日
 */
public class JSONResultDecoder implements Decoder {
	
	private static final Logger logger = LoggerFactory.getLogger(JSONResultDecoder.class);
	
	private final ObjectMapper mapper;
	
	private static final TypeFactory TYPE_FACTORY = TypeFactory.defaultInstance();

	/**
	 * Creates a new instance of JacksonDecoder
	 * 
	 * @param mapper
	 */
	public JSONResultDecoder(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * @see feign.jackson.JacksonDecoder#decode(feign.Response, java.lang.reflect.Type)
	 */
	@Override
	public Object decode(Response response, Type type) throws IOException {
		
		String body = "";
		try {
			if (response.body() == null)
				return null;
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
			JSONResult<?> result = mapper.readValue(body,
					TYPE_FACTORY.constructParametricType(JSONResult.class, TYPE_FACTORY.constructType(type)));
			logger.debug("receive data from remote service: \n" + result);
			return result.getData();
		} catch (RuntimeJsonMappingException e) {
			logger.error("remote service success, but parse error, response: " + body, e);
			throw new RemoteException("返回数据解析失败", e);
		}
	}
	
}
