package com.weichen.springbootmall.service;

import com.weichen.springbootmall.constant.ProductCategory;
import com.weichen.springbootmall.dto.ProductRequest;
import com.weichen.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category,String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
