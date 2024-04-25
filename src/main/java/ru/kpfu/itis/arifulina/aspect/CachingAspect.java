package ru.kpfu.itis.arifulina.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CachingAspect {

    private final HashMap<String, Object> cache;

    @Pointcut("@annotation(ru.kpfu.itis.arifulina.aspect.annotation.Cacheable)")
    public void cacheableMethod() {
    }

    @Pointcut("target(org.springframework.data.jpa.repository.JpaRepository)")
    public void jpaRepositoryMethod() {
    }

    @Around("cacheableMethod() && jpaRepositoryMethod()")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;

        String cacheKey = buildCacheKey(joinPoint);

        if (cache.containsKey(cacheKey)) {
            result = cache.get(cacheKey);
            log.info("Got {} from cache with cache key {}", result.toString(), cacheKey);
        } else {
            result = joinPoint.proceed();
            log.info("Got {} from {}", result.toString(), joinPoint.getSignature().getDeclaringTypeName());

            if (result instanceof Optional<?>) {
                if (((Optional<?>) result).isPresent()) {
                    cache.put(cacheKey, result);
                }
            }
            if (result instanceof Collection<?>) {
                if (!((Collection<?>) result).isEmpty()) {
                    cache.put(cacheKey, result);
                }
            }
        }

        return result;
    }

    private String buildCacheKey(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StringBuilder sb = new StringBuilder();

        sb.append(methodSignature.getName());
        sb.append(":");

        String[] paramNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            sb.append(paramNames[i]);
            sb.append("=");
            sb.append(args[i]);
            sb.append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
