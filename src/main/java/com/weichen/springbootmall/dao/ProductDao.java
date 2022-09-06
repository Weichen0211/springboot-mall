package com.weichen.springbootmall.dao;

import com.weichen.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
