/**
 * Copyright (c) 2016, micro All Rights Reserved.
 */

package micro.core.tool;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;


/**   
 * ClassName: JSONMapper
 * 根据客户端API及服务端SPI配置相应的Jackson ObjectMapper
 * 
 * @author 郑智文
 * @version 1.0
 * @CreateDate 2015-10-29 上午9:35:10
*/
public class JSONMapper extends ObjectMapper
{
    private static final long serialVersionUID = 7679503945581779104L;
    
    private static final String DEFAULT_DATE_PATTERN = Tools.DatePattern.TILL_SECOND_1;
    
    /**
     * Creates a new instance of JSONMapper
     * 
     */
    private JSONMapper() {
    	super();
    }
	
	/**
	 * 默认为服务端配置
	 */
    public static JSONMapper withSpiConfig(){
		return withSpiConfig(null);
	}
	public static JSONMapper withSpiConfig(String datePattern){
		JSONMapper jsonMapper = new JSONMapper();
		if(Tools.String.isBlank(datePattern)){
			datePattern = DEFAULT_DATE_PATTERN;
		}
		jsonMapper.setDateFormat(Tools.DateFormats.match(datePattern));
//		jsonMapper.setSerializationInclusion(Include.NON_NULL);
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return jsonMapper;
	}
	
	/**
	 * 客户端配置
	 */
	public static JSONMapper withApiConfig(){
		return withApiConfig(null);
	}
	public static JSONMapper withApiConfig(String datePattern){
		JSONMapper jsonMapper = new JSONMapper();
		if(Tools.String.isBlank(datePattern)){
			datePattern = DEFAULT_DATE_PATTERN;
		}
		jsonMapper.setDateFormat(Tools.DateFormats.match(datePattern));
		// 允许单引号
		jsonMapper.enable(Feature.ALLOW_SINGLE_QUOTES);
		// 字段和值都加引号
		jsonMapper.enable(Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		// 数字也加引号
		jsonMapper.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
		jsonMapper.enable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
		
		// 美化打印输出
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

		// 空值处理为空串
		jsonMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){

			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
					throws IOException, JsonProcessingException
			{
				jg.writeString("");
			}
		});
		return jsonMapper;
	}
}

