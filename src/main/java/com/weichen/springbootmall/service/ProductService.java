package com.weichen.springbootmall.service;

import com.weichen.springbootmall.dto.ProductRequest;
import com.weichen.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
