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

public class ProductsRemoveFromBucketController extends HttpServlet {

    public static final String ATTRIBUTE_BUCKET = "productsInBucket";
    public static final String PARAM_ID = "id";
    ProductDao productDao = new ProductDaoDemo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">> Inside ProductsRemoveFromBucketController");
        String idStr = req.getParameter(PARAM_ID);
        try {
            Integer id = Integer.valueOf(idStr);
            Product product = productDao.getProductById(id);
            HttpSession session = req.getSession();
            Map<Product, Integer> oldBucket = (Map<Product, Integer>) session.getAttribute(ATTRIBUTE_BUCKET);
            System.out.println(">> amount of products " + oldBucket.get(product));
            String productControllerId = ProductController.getIdStr();
            System.out.println(">> id to redirect: " + productControllerId);
            String newLocation = "product.do?id=" + productControllerId;
            if (oldBucket != null) {
                System.out.println(">> Inside oldBucket != null");
                if(oldBucket.containsKey(product)) {
                    System.out.println(">> Inside oldBucket.containsKey(product)");
                    Map<Product, Integer> newBucket = new LinkedHashMap<>(oldBucket);
                    Integer productsAmount = newBucket.get(product);
                        Integer newProductsAmount = --productsAmount;
                        if(newProductsAmount == 0){
                            newBucket.remove(product);
                            session.setAttribute(ATTRIBUTE_BUCKET, Collections.unmodifiableMap(newBucket));
                            resp.sendRedirect(newLocation);
                            return;
                        }
                        newBucket.put(product, newProductsAmount);
                        session.setAttribute(ATTRIBUTE_BUCKET, Collections.unmodifiableMap(newBucket));
                }
            }

            resp.sendRedirect(newLocation);
        } catch (DaoException ignore){
            //NOP
        }
    }
}
