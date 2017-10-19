/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.core.tool.extension;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import micro.core.tool.Tools;

/**
 * Spring容器SPI扩展接口注册器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月14日
 */
public class SpringExtensionRegistrar implements
								//BeanNameAware,
								ApplicationContextAware,
								InitializingBean,
								BeanDefinitionRegistryPostProcessor // extends BeanFactoryPostProcessor
								//BeanFactoryPostProcessor,
//								BeanPostProcessor
										{
	
	

	private static final String micro_AUTOWIRE_DIRECTORY = "META-INF/micro/beans/";
	
	private Set<Class<?>> classes = new HashSet<>();
	
//	private Set<String> cachedNames = new HashSet<>();
	
	private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringExtensionLoader.setApplicationContext(applicationContext);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
            Enumeration<java.net.URL> urls;
            ClassLoader classLoader = SpringExtensionRegistrar.class.getClassLoader();
            if (classLoader != null) {
                urls = classLoader.getResources(micro_AUTOWIRE_DIRECTORY);
            } else {
                urls = ClassLoader.getSystemResources(micro_AUTOWIRE_DIRECTORY);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL url = urls.nextElement();
                    Tools.File.listFiles(new File(url.toURI()), FileFilterUtils.trueFileFilter(),
                    		FileFilterUtils.directoryFileFilter()).forEach((file)->{
                    			Class<?> type;
								try {
									type = Class.forName(file.getName());
								} catch (Exception e) {
									throw new SpiLoadException("Exception when initialize class: " + file.getName() + ".", e);
								}
                    			SpiReader.read(file).forEach((name, clazz)->{
        	                    	if (! type.isAssignableFrom(clazz)) {
        	    		        		throw new IllegalStateException("Error when load extension class(interface: " +
        	    		        				type + "), class " + clazz.getName() + " is not subtype of " + type + ".");
        	    		        	}
        	    		        	if(classes.contains(clazz)) {
        	    		        		throw new IllegalStateException("Error when load extension class(interface: " +
        	    		        				type + "), can not set duplicated class: " + clazz.getName() + ".");
        	    		        	}
        	    		        	classes.add(clazz);
//    	    		        		Holder holder = holders.get(type);
//    	    		        		if(holder == null) {
//    	    		        			holder = new Holder(type);
//    	    		        			holders.put(type, holder);
//    	    		        		}
//    	    		        		holder.put(name, clazz);
//    	    		        		cachedNames.add(name);
        	                    });
                    		});;
                }
            }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		register(registry, SpringExtensionLoader.class);
		classes.forEach((clazz)->{
			register(registry, clazz);
		});
//		holders.forEach((type, holder)->{
//			Map<String, Class<?>> extensionClasses = holder.getCachedClasses();
//			if(!extensionClasses.isEmpty()){
//				BeanDefinitionBuilder holderBuilder = BeanDefinitionBuilder.genericBeanDefinition(ArrayList.class);
//				BeanDefinition holderDefinition = holderBuilder.getRawBeanDefinition();
////				holderDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
//				
//				String holderName = beanNameGenerator.generateBeanName(holderDefinition, registry);
//				System.out.println(holderName);
//				registry.registerBeanDefinition(holder.getName(), holderDefinition);
//				extensionClasses.forEach((name, clazz) -> {
//					BeanDefinitionBuilder instanceBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//					BeanDefinition instanceDefinition = instanceBuilder.getRawBeanDefinition();
//					String instanceName = beanNameGenerator.generateBeanName(instanceDefinition, registry);
//					System.out.println(instanceName);
//					registry.registerBeanDefinition(instanceName, instanceDefinition);
//				});
//			}
//		});
	}
	
	private void register(BeanDefinitionRegistry registry, Class<?> clazz) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		BeanDefinition definition = builder.getRawBeanDefinition();
		String instanceName = beanNameGenerator.generateBeanName(definition, registry);
		registry.registerBeanDefinition(instanceName, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//		if (configurableListableBeanFactory instanceof BeanDefinitionRegistry) {
//			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) configurableListableBeanFactory;
//		}
//		if (configurableListableBeanFactory instanceof DefaultListableBeanFactory) {
//			DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
//		}
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
//		return bean;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
//		return bean;
//	}

}
