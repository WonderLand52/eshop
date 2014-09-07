package com.rudenkoInc.eshop.inject;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class DependencyInjectionServlet extends HttpServlet{
    public static final String APP_CTX_PATH = "contextConfigLocation";
    @Override
    public void init() throws ServletException {
        String appCtxPath = this.getServletContext().getInitParameter(APP_CTX_PATH);
        System.out.println(">> loaded: " + APP_CTX_PATH + " -> " + appCtxPath);
        if(appCtxPath == null){
            System.err.println("I need init param: " + APP_CTX_PATH);
            throw new ServletException(APP_CTX_PATH + " init parameter == null");
        }

        try {
            ApplicationContext appContext = new ClassPathXmlApplicationContext(appCtxPath);

            List<Field> allFields = FieldReflector.collectAll(this.getClass(), DependencyInjectionServlet.class);
            List<Field> injectedFields = FieldReflector.filterInject(allFields);

            for (Field field: injectedFields){
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
                System.out.println("I've found method marked by @Inject: " + field);
                String beanName = annotation.value();
                System.out.println("I must instantiate and inject " + beanName);
                Object bean = appContext.getBean(beanName);
                if(bean == null){
                    throw new ServletException("There is no bean with name: " + beanName);
                }
                field.set(this, bean);
            }
        } catch (IllegalAccessException e){
           e.printStackTrace();
        }
    }
}
