/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.web.convert;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.TypeUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;

import micro.web.tool.ObjectMapperHolder;

/**
 * 存在字段权限时，需要根据动态ObjectMapper实例返回JSON数据（写数据）
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年9月5日
 */
public class DynamicMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	private static final MediaType TEXT_EVENT_STREAM = new MediaType("text", "event-stream");
	
	private PrettyPrinter ssePrettyPrinter;
	
	/**
	 * Creates a new instance of DynamicMappingJackson2HttpMessageConverter
	 */
	public DynamicMappingJackson2HttpMessageConverter() {
		super();
	}

	/**
	 * Creates a new instance of DynamicMappingJackson2HttpMessageConverter
	 * @param objectMapper
	 */
	public DynamicMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper);
	}
	
	protected void init(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		setDefaultCharset(DEFAULT_CHARSET);
		DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
		prettyPrinter.indentObjectsWith(new DefaultIndenter("  ", "\ndata:"));
		this.ssePrettyPrinter = prettyPrinter;
	}
	
	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		ObjectMapper objectMapper = ObjectMapperHolder.get();
		if(objectMapper == null) {
			objectMapper = this.objectMapper;
		}
		MediaType contentType = outputMessage.getHeaders().getContentType();
		JsonEncoding encoding = getJsonEncoding(contentType);
		JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
		try {
			writePrefix(generator, object);

			Class<?> serializationView = null;
			FilterProvider filters = null;
			Object value = object;
			JavaType javaType = null;
			if (object instanceof MappingJacksonValue) {
				MappingJacksonValue container = (MappingJacksonValue) object;
				value = container.getValue();
				serializationView = container.getSerializationView();
				filters = container.getFilters();
			}
			if (type != null && value != null && TypeUtils.isAssignable(type, value.getClass())) {
				javaType = getJavaType(type, null);
			}
			ObjectWriter objectWriter;
			if (serializationView != null) {
				objectWriter = objectMapper.writerWithView(serializationView);
			}
			else if (filters != null) {
				objectWriter = objectMapper.writer(filters);
			}
			else {
				objectWriter = objectMapper.writer();
			}
			if (javaType != null && javaType.isContainerType()) {
				objectWriter = objectWriter.forType(javaType);
			}
			SerializationConfig config = objectWriter.getConfig();
			if (contentType != null && contentType.isCompatibleWith(TEXT_EVENT_STREAM) &&
					config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
				objectWriter = objectWriter.with(this.ssePrettyPrinter);
			}
			objectWriter.writeValue(generator, value);

			writeSuffix(generator, object);
			generator.flush();

		}
		catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write content: " + ex.getMessage(), ex);
		}
		ObjectMapperHolder.clear();
	}

}
