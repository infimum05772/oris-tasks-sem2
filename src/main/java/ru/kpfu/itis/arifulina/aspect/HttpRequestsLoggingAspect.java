package ru.kpfu.itis.arifulina.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpRequestsLoggingAspect {
    public static final String NOT_AUTHENTICATED = "anonymous";

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Pointcut("@annotation(ru.kpfu.itis.arifulina.aspect.annotation.HttpRequest)")
    public void httpRequest() {}

    @Pointcut("@target(org.springframework.stereotype.Controller)")
    public void controllerMethod() {
    }

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethod() {
    }

    @Around("httpRequest() && (controllerMethod() || restControllerMethod())")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;

        Principal principal =  request.getUserPrincipal();
        String principalName = principal == null ? NOT_AUTHENTICATED : principal.getName();

        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        result = joinPoint.proceed();
        LocalDateTime time = LocalDateTime.now();
        int code = response.getStatus();

        log.info("{}.{}: {} request with URL {} made by {} processed with code {} at {} ", controllerName, methodName, method, url, principalName, code, time);

        return result;
    }
}
