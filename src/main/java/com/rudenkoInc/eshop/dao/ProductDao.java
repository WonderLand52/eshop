package com.rudenkoInc.eshop.dao;


import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;
import com.rudenkoInc.eshop.dao.exceptions.EntityNotFoundException;
import com.rudenkoInc.eshop.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    public Product getProductById(int id) throws DaoException;

    public List<Product> getAllProducts() throws DaoException;
}
