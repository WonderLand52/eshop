package com.rudenkoInc.eshop.dao.impl;


import com.rudenkoInc.eshop.dao.ProductDao;
import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;
import com.rudenkoInc.eshop.dao.exceptions.EntityNotFoundException;
import com.rudenkoInc.eshop.dao.externalTxDao.impl.JdbcUtils;
import com.rudenkoInc.eshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class ProductDaoExternalTxImpl implements ProductDao{

    public static final String GET_PRODUCT_BY_ID_SQL = "SELECT productName FROM products WHERE id = ?";
    public static final String GET_ALL_PRODUCTS_SQL = "SELECT id, productName FROM products";

    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Product getProductById(int id) throws DaoException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt =  dataSource.getConnection().prepareStatement(GET_PRODUCT_BY_ID_SQL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(!rs.next()){
                throw new NoSuchElementException("No product for id: " + id);
            }
            return new Product(id, rs.getString("productName"));
        } catch (SQLException e){
            throw new DaoSystemException("Some exception: " + e.getMessage());
        } finally {
            JdbcUtils.closeQuietly(rs, stmt);
        }

    }

    @Override
    public List<Product> getAllProducts() throws DaoException {
        try{
            Statement stmt = dataSource.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_PRODUCTS_SQL);
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()){
                Product product = new Product(rs.getInt("id"), rs.getString("productName"));
                products.add(product);
            }
            return products;
        } catch (SQLException e){
            throw new DaoSystemException("Some exception: " + e.getMessage());
        }



    }
}
