package com.example.helloWorld.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Aspect
@Component
public class UserDetailsServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Clock clock = Clock.systemDefaultZone();

    @Before(value = "execution(* com.example.helloWorld.service.UserDetailsService.*(..)) && args(username)")
    public void beforeAdvice(JoinPoint joinPoint, String username) {
        logger.info("Method: {}, started at {} " , joinPoint.getSignature(), clock.instant());
        logger.info("Pretending to authenticate user with username - {}. ", username );
    }

    @AfterReturning(value = "execution(* com.example.helloWorld.service.UserDetailsService.*(..)) && args(username)")
    public void afterAdvice(JoinPoint joinPoint, String username) {
        logger.info("Method: {}, finished at {} " , joinPoint.getSignature(), clock.instant());
        logger.info("Successfully authenticated user with username - {}" , username);
    }

    @AfterThrowing(value = "execution(* com.example.helloWorld.service.UserDetailsService.*(..))", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        logger.info("After method: " + joinPoint.getSignature());
        logger.warn("There has been an exception: " + error);
    }
}
