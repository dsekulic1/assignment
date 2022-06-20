package com.example.helloWorld.aspect;

import com.example.helloWorld.model.Language;
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
public class LanguageServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Clock clock = Clock.systemDefaultZone();

    @Before(value = "execution(* com.example.helloWorld.service.LanguageService.*(..)) && args(language)")
    public void beforeAdvice(JoinPoint joinPoint, Language language) {
        logger.info("Method: {}, started at {}." , joinPoint.getSignature(), clock.instant());
        logger.info("Creating Language with language name - {} and message {}." , language.getLanguage(), language.getMessage());
    }

    @AfterReturning(value = "execution(* com.example.helloWorld.service.LanguageService.*(..)) && args(language)")
    public void afterAdvice(JoinPoint joinPoint, Language language) {
        logger.info("Method: {}, finished at {}." , joinPoint.getSignature(), clock.instant());
        logger.info("Successfully created Language with language name - {} and message {}." , language.getLanguage(), language.getMessage());
    }

    @Before(value = "execution(* com.example.helloWorld.service.LanguageService.*(..)) && args(language)")
    public void beforeAdvice(JoinPoint joinPoint, String language) {
        logger.info("Method: {}, started at {}." , joinPoint.getSignature(), clock.instant());
        logger.info("Pretending to find message for language name - {}.", language);
    }

    @AfterReturning(value = "execution(* com.example.helloWorld.service.LanguageService.*(..)) && args(language)")
    public void afterReturning(JoinPoint joinPoint, String language) {
        logger.info("Method: {}, finished at {}." , joinPoint.getSignature(), clock.instant());
        logger.info("Successfully found message for language name - {}.", language);
    }

    @AfterThrowing(value = "execution(* com.example.helloWorld.service.LanguageService.*(..))", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        logger.info("After method: {}.",joinPoint.getSignature());
        logger.warn("There has been an exception: " + error);
    }
}
