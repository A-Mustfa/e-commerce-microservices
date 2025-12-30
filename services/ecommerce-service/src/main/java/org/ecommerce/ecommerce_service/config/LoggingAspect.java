package org.ecommerce.ecommerce_service.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* org.ecommerce.ecommerce_service.services.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info("method: " + methodName + " has args: {} ", Arrays.toString(methodArgs));
        long before = System.currentTimeMillis();
        Object returnedArgs = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - before;
        if( executionTime > 1000){
            log.warn("the execution time of: {} , is : {} ",
                    joinPoint.getSignature().getName(),
                    executionTime);
        }
        log.info("method: {} returned: {} ",joinPoint.getSignature().getName(),returnedArgs);
        return  returnedArgs;
    }

}
