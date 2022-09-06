package com.weichen.springbootmall.service.impl;

import com.weichen.springbootmall.dao.ProductDao;
import com.weichen.springbootmall.model.Product;
import com.weichen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao ;

    @Override
    public Product getProductById(Integer productId) {

        return productDao.getProductById(productId);
    }
}
