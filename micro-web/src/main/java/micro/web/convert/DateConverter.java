package micro.web.convert;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import micro.core.tool.Tools;

/**   
 * ClassName: StandardDateConverter
 * @description: 日期转换器（支持所有默认格式）
 * 
 * @author 郑智文
 * @version 1.0
 * @CreateDate 2015-8-11 上午11:27:42
 */
public class DateConverter implements Converter<String, Date> {

	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public Date convert(String date) {
		return Tools.DateFormats.parse(date);
	}
}
