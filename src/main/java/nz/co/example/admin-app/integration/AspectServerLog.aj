//package nz.co.example.dev.integration;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StopWatch;
//
//public aspect AspectServerLog {
//
//    public static final Logger ERR_LOG = LoggerFactory.getLogger("error");
//
//    Object around() : call (* nz.co.example.dev.integration.tx.* (..)) {
//
//        StopWatch watch = new StopWatch();
//        watch.start(thisJoinPoint.toShortString());
//
//        try {
//            return proceed();
//        } finally {
//            watch.stop();
//        }
//    }
//
//    after() throwing (Exception ex) : call (* nz.co.example.dev.integration.tx.* (..)) {
//        StringBuilder mesg = new StringBuilder("Exception in ");
//        mesg.append(thisJoinPoint.toShortString()).append('(');
//        for (Object o : thisJoinPoint.getArgs()) {
//            mesg.append(o).append(',');
//        }
//        mesg.append(')');
//
//        ERR_LOG.error(mesg.toString(), ex);
//    }
//}