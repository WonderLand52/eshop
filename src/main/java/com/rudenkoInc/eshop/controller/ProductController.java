package com.rudenkoInc.eshop.controller;


import com.rudenkoInc.eshop.dao.ProductDao;
import com.rudenkoInc.eshop.dao.WorkToDoInterface.UnitOfWork;
import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;
import com.rudenkoInc.eshop.dao.exceptions.EntityNotFoundException;
import com.rudenkoInc.eshop.dao.externalTxDao.TransactionsManager;
import com.rudenkoInc.eshop.entity.Product;
import com.rudenkoInc.eshop.inject.DependencyInjectionServlet;
import com.rudenkoInc.eshop.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class ProductController extends DependencyInjectionServlet{

    public final static String PARAM_ID = "id";
    public final static String ATTRIBUTE_PRODUCT = "product";
    public final static String PAGE_OK = "product.jsp";
    public final static String PAGE_ERROR = "error.jsp";

    @Inject("txManager")
    TransactionsManager txManager;

    @Inject("productDao")
    ProductDao productDao;

    private static String idStr;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">> Inside ProductController doGet()");
        idStr = req.getParameter(PARAM_ID);
        if(idStr != null){
            final Integer productId = Integer.valueOf(idStr);
            try{
                Product product = txManager.doInTransaction(new UnitOfWork<Product, DaoException>() {
                    @Override
                    public Product doInTx() throws DaoException {
                        return productDao.getProductById(productId);
                    }
                });
                req.setAttribute(ATTRIBUTE_PRODUCT, product);
                req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                return;
            } catch (DaoException | NumberFormatException ex){
                ex.printStackTrace();
            }
        }
            resp.sendRedirect(PAGE_ERROR);
    }

    public static String getIdStr(){
        return idStr;
    }
}
