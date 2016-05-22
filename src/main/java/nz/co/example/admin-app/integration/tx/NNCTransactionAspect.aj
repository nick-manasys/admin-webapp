package nz.co.example.dev.integration.tx;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.omg.IOP.TransactionService;

/**
 * NNCTransactionAspect used to provide advice to transactions.
 * 
 * @author Nick
 * 
 */
public aspect NNCTransactionAspect {
    // private TransactionService transactionService = new TransactionServiceNull();

    @Pointcut("execution(* nz.co.example.wdev.integration..*.*(..))")
    public void inServiceLayer() {
    };

    @Pointcut("execution(@org.springframework.transaction.annotation.Transactional * *(..))")
    public void transactionalMethod() {
    }

    @Before("transactionalMethod() && inServiceLayer()")
    public void beforeTransactionalMethod(JoinPoint joinPoint) {
        System.out.print(">> beginTransaction");
        // transactionService.beginTransaction();
    }

    @AfterReturning("transactionalMethod() && inServiceLayer()")
    public void afterTransactionalMethod(JoinPoint joinPoint) {
        System.out.print(">> commit");
        // transactionService.commit();
    }

    @AfterThrowing(pointcut = "transactionalMethod() && inServiceLayer()" /* , throwing = "e" */)
    public void afterThrowingFromTransactionalMethod(JoinPoint joinPoint /* , RuntimeException e */) {
        System.out.print(">> rollback");
        // transactionService.rollback();
    }

    public void setTransactionService(final TransactionService transactionService) {
        System.out.print("<<>>");
        // this.transactionService = transactionService;
    }
}
