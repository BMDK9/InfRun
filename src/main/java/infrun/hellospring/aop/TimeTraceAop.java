package infrun.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component // Aop set 2 Component 등록 시
public class TimeTraceAop {

//    @Around("execution(* infrun.hellospring..*(..))") // Aop set 2 Component 등록 시
    // set 1(Config에서 직접 Bean 등록)
    @Around("execution(* infrun.hellospring..*(..)) && !target(infrun.hellospring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
