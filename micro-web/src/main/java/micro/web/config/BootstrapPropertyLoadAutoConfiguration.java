package micro.web.config;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import micro.core.tool.Tools;
import micro.core.tool.extension.MicroExtensionLoader;
import micro.web.extension.spi.PropertyBootstrapBinder;
import micro.web.extension.spi.PropertyBootstrapLoader;
import micro.web.tool.PropertiesHolder;

/**
 * 统一配置初始化加载类
 * spring.factories:org.springframework.cloud.bootstrap.BootstrapConfiguration
 * eg. 	{@link org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration
 * 						#initialize(ConfigurableApplicationContext)}
 * 	&	{@link org.springframework.cloud.config.client.ConfigServicePropertySourceLocator
 * 						#locate(org.springframework.core.env.Environment)}
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年5月16日
 */
@Configuration
public class BootstrapPropertyLoadAutoConfiguration
		implements ApplicationContextInitializer<ConfigurableApplicationContext>,
				Ordered  {
	
	private static final Logger logger = LoggerFactory.getLogger(BootstrapPropertyLoadAutoConfiguration.class);
	
	/**
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 20;
	}
	
//	@Bean
//	public ServiceListFactoryBean propertyBootstrapBinders(){
//		ServiceListFactoryBean serviceListFactoryBean = new ServiceListFactoryBean();
//        serviceListFactoryBean.setServiceType(PropertyBootstrapBinder.class);
//        return serviceListFactoryBean;
//	}
//	
//	@Bean
//	public ServiceListFactoryBean propertyBootstrapLoaders(){
//		ServiceListFactoryBean serviceListFactoryBean = new ServiceListFactoryBean();
//		serviceListFactoryBean.setServiceType(PropertyBootstrapLoader.class);
//		return serviceListFactoryBean;
//	}
//	
//	@Bean
//	public ServiceLoaderFactoryBean webMvcPreHandleInterceptors(){
//		ServiceLoaderFactoryBean factoryBean = new ServiceLoaderFactoryBean();
//        factoryBean.setServiceType(WebMvcPreHandleInterceptor.class);
//        return factoryBean;
//	}

	/**
	 * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.ConfigurableApplicationContext)
	 */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		List<PropertyBootstrapBinder> propertyootstrapBinders
					= MicroExtensionLoader.getInstance(PropertyBootstrapBinder.class).get();
		List<PropertyBootstrapLoader> propertyBootstrapLoaders
					= MicroExtensionLoader.getInstance(PropertyBootstrapLoader.class).get();
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		if(Tools.Collection.isNotEmpty(propertyootstrapBinders)) {
			for(PropertyBootstrapBinder binder : propertyootstrapBinders) {
				List<String> propertyKeys = binder.propertyKeys();
				if(Tools.Collection.isNotEmpty(propertyKeys)) {
					for(String propertyKey : propertyKeys) {
						String propertyValue = environment.resolvePlaceholders("${"+propertyKey+":}");
						logger.debug("load property in bootstrap: {}={}", propertyKey, propertyValue);
						PropertiesHolder.put(propertyKey, propertyValue);
					}
				}
			}
		}
		if(Tools.Collection.isNotEmpty(propertyBootstrapLoaders)) {
			for(PropertyBootstrapLoader loader : propertyBootstrapLoaders) {
				PropertySource<?> propertySource = loader.load();
				if(propertySource != null){
					propertySources.addLast(propertySource);
				}
			}
		}
	}

}
