///**
// * 
// */
//package nz.co.example.dev.integration;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StopWatch;
//
///**
// * @author Nick
// * 
// */
//public aspect LoggingAspect {
//    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
//
//    @Pointcut("execution(* nz.co.example.dev.integration.tx..*.*(..))")
//    public void loggableOperation() {
//    }
//
//    @AfterThrowing(pointcut = "loggableOperation()", throwing = "exception")
//    public void logException(Throwable exception) {
//        System.out.println(exception.getMessage());
//    }
//
//    // @Around("loggableOperation()")
//    // public void swallowException(ProceedingJoinPoint pjp) throws Throwable {
//    // try {
//    // pjp.proceed();
//    // } catch (Throwable exception) {
//    // System.out.println(exception.getMessage());
//    // }
//    // }
//
//    // @Around("execution(* nz.co.example.dev.integration.tx..*.*(..))")
//    @Around("loggableOperation()")
//    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        Object result = joinPoint.proceed();
//
//        stopWatch.stop();
//
//        StringBuffer logMessage = new StringBuffer();
//        logMessage.append(joinPoint.getTarget().getClass().getName());
//        logMessage.append(".");
//        logMessage.append(joinPoint.getSignature().getName());
//        logMessage.append("(");
//        // append args
//        Object[] args = joinPoint.getArgs();
//        for (int i = 0; i < args.length; i++) {
//            logMessage.append(args[i]).append(",");
//        }
//        if (args.length > 0) {
//            logMessage.deleteCharAt(logMessage.length() - 1);
//        }
//
//        logMessage.append(")");
//        logMessage.append(" execution time: ");
//        logMessage.append(stopWatch.getTotalTimeMillis());
//        logMessage.append(" ms");
//        logger.info(logMessage.toString());
//        return result;
//    }
//}
