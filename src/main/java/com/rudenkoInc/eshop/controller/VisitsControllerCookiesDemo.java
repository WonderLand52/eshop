package com.rudenkoInc.eshop.controller;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class VisitsControllerCookiesDemo extends HttpServlet{

    public static final String COOKIE_NAME_COUNTER = "counter";
    public static final String VALUE_INITIAL_COUNTER = "1";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(COOKIE_NAME_COUNTER)){
                    AtomicInteger numberOfVisits = new AtomicInteger(Integer.valueOf(cookie.getValue()));
                    numberOfVisits.incrementAndGet();
                    resp.addCookie(new Cookie(COOKIE_NAME_COUNTER, String.valueOf(numberOfVisits)));
                    resp.getWriter().write("You've visited this page " + numberOfVisits + " times.");
                    return;
                }
            }
        }
        createNewCookieCounter(resp);

    }

    private void createNewCookieCounter(HttpServletResponse resp) throws IOException {
        Cookie cookie = new Cookie(COOKIE_NAME_COUNTER, VALUE_INITIAL_COUNTER);
        resp.addCookie(cookie);
        resp.getWriter().write("You've visited this page 1 time.");
    }
}
