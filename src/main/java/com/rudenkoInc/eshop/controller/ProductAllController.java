package com.rudenkoInc.eshop.controller;


import com.rudenkoInc.eshop.dao.ProductDao;
import com.rudenkoInc.eshop.dao.WorkToDoInterface.UnitOfWork;
import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.externalTxDao.TransactionsManager;
import com.rudenkoInc.eshop.entity.Product;
import com.rudenkoInc.eshop.inject.DependencyInjectionServlet;
import com.rudenkoInc.eshop.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class ProductAllController extends DependencyInjectionServlet {

    public static final String ATTRIBUTE_ALL_PRODUCTS = "allProducts";
    public static final String PAGE_ALL_PRODUCTS = "productsAll.jsp";
    public static final String PAGE_ERROR = "error.jsp";

    @Inject("productDao")
    ProductDao productDao;

    @Inject("txManager")
    TransactionsManager txManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">> Inside ProductsAllController.doGet():");
        try {
            List<Product> products = txManager.doInTransaction(new UnitOfWork<List<Product>, DaoException>() {
                @Override
                public List<Product> doInTx() throws DaoException {
                    return productDao.getAllProducts();
                }
            });
            if(products != null){
                req.setAttribute(ATTRIBUTE_ALL_PRODUCTS, products);
                req.getRequestDispatcher(PAGE_ALL_PRODUCTS).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(PAGE_ERROR);

    }
}
