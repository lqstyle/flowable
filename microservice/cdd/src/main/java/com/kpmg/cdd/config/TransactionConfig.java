package com.kpmg.cdd.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TransactionConfig {


    public static final String transactionExecution = "execution (public * com.kpmg.cdd..service..*(..))";



    //@Bean
    //public AspectJExpressionPointcut aspectJExpressionPointcut(){
    //    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    //    pointcut.setExpression(transactionExecution);
    //    return pointcut;
    //}

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(PlatformTransactionManager platformTransactionManager){
        //AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //pointcut.setExpression(transactionExecution);
        //DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        //advisor.setPointcut(pointcut);
        //advisor.setAdvice(transactionInterceptor());
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(transactionExecution);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        Properties attributes = new Properties();
        attributes.setProperty("*", "PROPAGATION_REQUIRED,-Exception");
        TransactionInterceptor txAdvice = new TransactionInterceptor(platformTransactionManager,attributes);
        advisor.setAdvice(txAdvice);
        return advisor;
    }


}
