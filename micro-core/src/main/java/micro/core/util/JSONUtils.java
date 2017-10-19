package micro.core.util;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import micro.core.tool.JSONMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON、java对象序列化工具类
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月16日
 */
public class JSONUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	
	/**
	 * @param target 目标对象 
	 * @param dateFormat 日期格式
	 * @return 字符串
	 */
	public static String fromObject(Object target, String dateFormat) {
		try {
			ObjectMapper mapper = JSONMapper.withApiConfig(dateFormat);
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, target);
			return writer.toString();
		} catch (Exception e) {
			logger.error("{} serialize to JSON error: {}", target.getClass().getName(), target);
			return null;
		}
	}
	
    /**
     * @see #fromObject(Object, String)
     */
    public static String fromObject(Object data) {
    	return fromObject(data, null);
    }
    
    /**
     * 
     * @param json	JSON字符串
     * @param beanType	转换的对象
     * @param dateFormat 日期格式
     * @return
     */
    public static <T> T toObject(String json, Class<T> beanType, String dateFormat) {
        try {
        	ObjectMapper mapper = JSONMapper.withApiConfig(dateFormat);
        	return mapper.readValue(json, beanType);
        } catch (Exception e) {
        	logger.error("Parse JSON string to {} error: {}", beanType.getName(), json);
        	return null;
        }
    }
    
    /**
     * @see #toObject(String, Class, String)
     */
    public static <T> T toObject(String json, Class<T> beanType) {
    	return toObject(json, beanType, null);
    }
    
    /**
     * 
     * @param json	JSON字符串
     * @param beanType	转换的对象
     * @param dateFormat 日期格式
     * @return
     */
	public static <T> List<T> toList(String json, Class<T> beanType, String dateFormat) {
    	try {
    		ObjectMapper mapper = JSONMapper.withApiConfig(dateFormat);
//	    	JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
    		return mapper.readValue(json, new TypeReference<List<T>>(){});
		} catch (Exception e) {
			logger.error("Parse JSON string to List<{}> error: {}", beanType.getSimpleName(), json);
			return null;
		}
    }
	
	/**
	 * @see #toList(String, Class, String)
	 */
	public static <T> List<T> toList(String json, Class<T> beanType) {
		return toList(json, beanType, null);
	}
    
    /**
     * @param json JSON字符串
     * @param keyType 键类型
     * @param valueType 值类型
     * @param dateFormat 日期格式
     * @return
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyType, Class<V> valueType, String dateFormat) {
    	try {
    		ObjectMapper mapper = JSONMapper.withApiConfig(dateFormat);
    		return mapper.readValue(json, new TypeReference<Map<K, V>>(){});
		} catch (Exception e) {
			logger.error("Parse JSON string to Map<{}, {}> error: {}", new Object[]{keyType.getSimpleName(),
					valueType.getSimpleName(), json});
			return null;
		}
    }
    
    /**
     * @see #toMap(String, Class, Class, String)
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyType, Class<V> valueType) {
    	return toMap(json, keyType, valueType, null);
    }
    
}
