package com.kpmg.cdd.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class MethodAspectConfig {
    private final static Logger log=LoggerFactory.getLogger(MethodAspectConfig.class);

    @Pointcut("execution(public * com.kpmg.cdd.service.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void deoBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        log.debug("begin - method[{}],args[{}]", methodName, args);
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        log.debug("end - method[{}]", methodName);
    }

    @AfterReturning(returning="result",pointcut="log()")
    public void doAfterReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        log.debug("return - method[{}],result[{}]", methodName, result);
    }

    @AfterThrowing(throwing = "e",pointcut = "log()")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        log.error("throw - method[{}]，throw exception[{}]", methodName, e);
    }

}
