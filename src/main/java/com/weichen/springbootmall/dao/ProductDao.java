package com.weichen.springbootmall.dao;

import com.weichen.springbootmall.dto.ProductRequest;
import com.weichen.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
