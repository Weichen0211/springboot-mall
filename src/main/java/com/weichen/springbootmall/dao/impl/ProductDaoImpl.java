package com.weichen.springbootmall.dao.impl;

import com.weichen.springbootmall.dao.ProductDao;
import com.weichen.springbootmall.dto.ProductQueryParams;
import com.weichen.springbootmall.dto.ProductRequest;
import com.weichen.springbootmall.model.Product;
import com.weichen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //查詢商品列表
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql = "SELECT product_id,product_name, category, " +
                "image_url, price, stock, description, created_date, " +
                "last_modified_date " +
                "FROM product WHERE 1=1";   // 1=1 不會對查詢結果有影響，主要使用的理由是要將下面的SQL指令自由拼接在SQL語法後面

        Map<String, Object> map = new HashMap<>();

        //查詢的條件 ex:商品類型 汽車、食物
        if(productQueryParams.getCategory() != null) {
            sql = sql + " AND category = :category" ;
            //因category 為enum類型，故使用上要用name方法，將enum類型轉為字串，在加入到map上
            map.put("category", productQueryParams.getCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search";

            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        //商品排序
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //商品分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());



        List<Product> productList = namedParameterJdbcTemplate.query(sql,map, new ProductRowMapper());

        return productList;
    }

    //按照ID查詢
    @Override
    public Product getProductById(Integer productId) {

        String sql = "SELECT product_id,product_name, category, " +
                "image_url, price, stock, description, created_date, " +
                "last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        }else{
            return null;
        }

    }


    //新增
    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date)" +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        //儲存資料庫生成的id
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    //修改
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_Url = :imageUrl,  "+
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    //刪除
    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_Id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql,map);

    }


}
