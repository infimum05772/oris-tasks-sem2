package ru.kpfu.itis.arifulina.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(ru.kpfu.itis.arifulina.aspect.annotation.Loggable)")
    public void loggableMethod() {
    }

    @Around("loggableMethod()")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getName();
        String methodName = methodSignature.getName();

        Object result;
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        result = joinPoint.proceed();
        stopWatch.stop();

        log.info("Execution time of {}.{} = {} ms", className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }

    @AfterThrowing(pointcut = "loggableMethod()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringType().getName();
        String methodName = joinPoint.getSignature().getName();

        log.error("Exception caught while processing {}.{}: {} - {}", className, methodName, ex.getClass(), ex.getMessage(), ex);
    }
}
