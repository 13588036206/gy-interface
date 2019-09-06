package com.gy.gyweb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 第一个*表示包下的所有类，第二个*表示类下的所有方法
     */
    @Pointcut("execution(* com.gy.gyservice.impl.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void before(JoinPoint point) {
        LOG.info("--开始执行类"+point.getSignature().getDeclaringTypeName()+"方法"+point.getSignature().getName()+"--");
    }

    @After("webLog()")
    public void after(JoinPoint point) {
        LOG.info("--类"+point.getSignature().getDeclaringTypeName()+"方法"+point.getSignature().getName()+"执行结束--");
    }
}
