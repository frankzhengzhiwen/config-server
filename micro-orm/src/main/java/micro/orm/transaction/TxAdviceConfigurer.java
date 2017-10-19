/**
 * Copyright (c) 2017, micro All Rights Reserved.
 */  
 
package micro.orm.transaction;


import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Aspect
@Configuration
@EnableConfigurationProperties(TxAdviceProperties.class)
public class TxAdviceConfigurer {

    @Autowired
    private TxAdviceProperties txAdviceProperties;

	/**
	 * Creates a new instance of TxAdviceConfigurer
	 */
	public TxAdviceConfigurer() {
		super();
	}

	@Bean
	public Advisor defaultPointcutAdvisor(PlatformTransactionManager transactionManager) {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setProperties(txAdviceProperties.getAttributes());
        
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager, source);
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(txAdviceProperties.getAdvisorPointCut());
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setPointcut(pointcut);
		advisor.setAdvice(transactionInterceptor);
		return advisor;
	}

}