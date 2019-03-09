package com.rh.commonutils.aoputils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AspectStyle {

//    /**
//     * 定义一个切面：
//     */
//    @Before("execution(* com.sxit..*.*(..))")
//     public void before(){
//         System.out.println("方法执行前执行.....");
//     }
//
//    /**
//     * 后置返回通知：
//     */
//    @AfterReturning("execution(* com.sxit..*.*(..))")
//    public void afterReturning(){
//        System.out.println("方法执行完执行.....");
//    }
//
//    @Around("execution(* com.sxit..*.*(..))")
//    public Object around(ProceedingJoinPoint pjp){
//        System.out.println("方法环绕start.....");
//        try {
//            pjp.proceed();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        System.out.println("方法环绕end.....");
//        return  null;
//    }
//
    @Pointcut("execution(* com.sxit..*.*(..))")
    public void init(){

    }

    @Before(value="init()")
    public void before1(){
        System.out.println("方法执行前执行.....");
    }

    @AfterReturning(value="init()")
    public void afterReturning1(){
        System.out.println("方法执行完执行.....");
    }

    @AfterThrowing(value="init()")
    public void throwss(){
        System.out.println("方法异常时执行.....");
    }

    @After(value="init()")
    public void after(){
        System.out.println("方法最后执行.....");
    }

    @Around(value="init()")
    public Object around1(ProceedingJoinPoint pjp){
        System.out.println("方法环绕start.....");
        Object o = null;
        try {
            o = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("方法环绕end.....");
        return o;
    }

//    作者：依木前行
//    来源：CSDN
//    原文：https://blog.csdn.net/two_people/article/details/52181599
//    版权声明：本文为博主原创文章，转载请附上博文链接！

}
