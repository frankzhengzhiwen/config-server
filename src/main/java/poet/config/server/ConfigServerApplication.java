package poet.config.server;
/**
 * Copyright (c) 2017, poet All Rights Reserved. 
 */ 

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置管理中心
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年4月23日
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		EnvironmentController
		new SpringApplicationBuilder(ConfigServerApplication.class).web(true).run(args);
	}

}
