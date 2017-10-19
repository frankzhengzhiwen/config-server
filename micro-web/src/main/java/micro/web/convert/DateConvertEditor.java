package micro.web.convert;

import java.beans.PropertyEditorSupport;

import micro.core.tool.Tools;
/**
 * 日期格式转化
 * @author 郑智文
 * @version:1.0 2014-09
 */
public class DateConvertEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String date) throws IllegalArgumentException {
        setValue(Tools.DateFormats.parse(date));
	}
}
