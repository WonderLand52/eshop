package com.rudenkoInc.eshop.controller;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class VisitsControllerDemo extends HttpServlet {

    public static final String PAGE_OK = "visits.jsp";
    public static final String ATTRIBUTE_COUNTER = "counter";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        AtomicInteger counter = (AtomicInteger) session.getAttribute("counter");
        if(counter == null){
            counter = new AtomicInteger(1);
            session.setAttribute(ATTRIBUTE_COUNTER, counter);
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
            return;
        }
        AtomicInteger numberOfVisits = new AtomicInteger(counter.incrementAndGet());
        session.setAttribute(ATTRIBUTE_COUNTER, numberOfVisits);
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);


    }
}
