package com.rudenkoInc.eshop.aop;


import org.aspectj.lang.ProceedingJoinPoint;

public class SimpleProfiler {

    public Object profile(ProceedingJoinPoint join, int id) throws Throwable{
        long t0 = System.nanoTime();
        try {
            return join.proceed();

        } finally {
            long t1 = System.nanoTime();
            System.out.println("ASPECT.PROFILER: " + join.toShortString() + " dT: " + (t1 - t0));
        }
    }
}
