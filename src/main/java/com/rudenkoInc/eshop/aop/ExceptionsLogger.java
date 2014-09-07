package com.rudenkoInc.eshop.aop;


import org.aspectj.lang.JoinPoint;

public class ExceptionsLogger {

    public void logException(JoinPoint joinPoint, Throwable t){
        System.out.println("EXCEPTION.LOGGER: " + t.getMessage());
    }
}
