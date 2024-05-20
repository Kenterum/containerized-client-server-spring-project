package az.edu.ada.ass3adaclient.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* az.edu.ada.ass3adaclient.service.BookServiceClient.*(..))")
    public void bookServiceClientMethods() {
    }

    @Around("bookServiceClientMethods()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Method called: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Exception in method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs(), throwable);
            throw throwable;
        }

        long endTime = System.currentTimeMillis();
        logger.info("Method executed: {} with result: {}", joinPoint.getSignature(), result);
        logger.info("Execution time: {} ms", (endTime - startTime));

        return result;
    }
}
