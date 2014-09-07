package com.rudenkoInc.eshop.controller;


import com.rudenkoInc.eshop.dao.ProductDao;
import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;
import com.rudenkoInc.eshop.dao.exceptions.EntityNotFoundException;
import com.rudenkoInc.eshop.dao.impl.ProductDaoDemo;
import com.rudenkoInc.eshop.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductsInBucketController extends HttpServlet{

    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_BUCKET = "productsInBucket";

    ProductDao productDao = new ProductDaoDemo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter(PARAM_ID);
        if(idStr != null){
            try {
                Integer id = Integer.valueOf(idStr);
                Product product = productDao.getProductById(id);
                HttpSession session = req.getSession(true);
                Map<Product, Integer> oldBucket = (Map<Product, Integer>) session.getAttribute(ATTRIBUTE_BUCKET);
                if (oldBucket == null) {
                    session.setAttribute(ATTRIBUTE_BUCKET, Collections.singletonMap(product, 1));
                } else {
                    Map<Product, Integer> newBucket = new LinkedHashMap<>(oldBucket);
                    if(!oldBucket.containsKey(product)){
                        newBucket.put(product, 1);
                    } else {
                        newBucket.put(product, newBucket.get(product) + 1);
                    }
                    System.out.println(">> number of this product" + newBucket.get(product));
                    session.setAttribute(ATTRIBUTE_BUCKET, Collections.unmodifiableMap(newBucket));
                }
                String newLocation = "product.do?id=" + id;
                resp.sendRedirect(newLocation);
                return;

            } catch (DaoException ignore){
                //NOP
            }
        }
        resp.sendRedirect("error.jsp");
    }
}
