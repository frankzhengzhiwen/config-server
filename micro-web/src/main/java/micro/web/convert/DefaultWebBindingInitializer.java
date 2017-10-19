package micro.web.convert;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
/**
 * 指定日期类型转化格式
 * @author 郑智文
 * @version:1.0 2014-09
 */
public class DefaultWebBindingInitializer extends ConfigurableWebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
    	super.initBinder(binder, request);
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
}
