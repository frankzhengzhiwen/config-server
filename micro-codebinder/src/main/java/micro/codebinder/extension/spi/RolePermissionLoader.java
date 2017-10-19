/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.codebinder.extension.spi;

import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.config.client.DiscoveryClientConfigServiceBootstrapConfiguration;
import org.springframework.core.env.Environment;

/**
 * 应用信息加载接口类
 * 启动前远程调用服务：	{@link DiscoveryClientConfigServiceBootstrapConfiguration#startup(org.springframework.context.event.ContextRefreshedEvent)}
 * 					{@link ConfigServicePropertySourceLocator#locate(Environment)}
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年7月17日
 */
public interface RolePermissionLoader {

}
