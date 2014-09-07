package com.rudenkoInc.eshop.dao.impl;

import com.rudenkoInc.eshop.dao.ProductDao;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;
import com.rudenkoInc.eshop.dao.exceptions.EntityNotFoundException;
import com.rudenkoInc.eshop.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ProductDaoDemo implements ProductDao {

    Map<Integer, Product> productsContainer = new ConcurrentHashMap<>();

    public ProductDaoDemo(){
        productsContainer.put(1, new Product(1, "Bread"));
        productsContainer.put(2, new Product(2, "Sugar"));
        productsContainer.put(3, new Product(3, "Paper"));
    }

    @Override
    public Product getProductById(int id) throws DaoSystemException, EntityNotFoundException {
        if(!productsContainer.containsKey(id)){
            System.out.println("Exception!!!");
            throw new EntityNotFoundException("There is no product by id: " + id + ".");
        }
        return productsContainer.get(id);
    }

    @Override
    public List<Product> getAllProducts() throws DaoSystemException {
        return new ArrayList<>(productsContainer.values());
    }
}
