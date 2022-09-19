package com.weichen.springbootmall.controller;

import com.weichen.springbootmall.constant.ProductCategory;
import com.weichen.springbootmall.dto.ProductQueryParams;
import com.weichen.springbootmall.dto.ProductRequest;
import com.weichen.springbootmall.model.Product;
import com.weichen.springbootmall.service.ProductService;
import com.weichen.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品列表，用LIST查詢整個product，"/products"為一個資源，即使商品資訊不存在，但是products資源必然存在的，故須為200給前端
    //查詢商品分類、關鍵字查詢
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // @RequestParam 表示可從URL中得到的請求參數
            // 查詢條件 filtering，控制查詢條件的參數
            @RequestParam(required = false) ProductCategory category,  //required = false 表示此參數是可選的參數，不一定要帶上category的值
            @RequestParam(required = false) String search,

            // 排序 Sorting 控制商品的排序
            @RequestParam(defaultValue = "created_date") String orderBy,       //決定以甚麼欄位來排序
            @RequestParam(defaultValue = "desc") String sort,           //決定從小排到大還是反過來

            //分頁功能 Pagination @Max、@Min 限制前端傳來的值不得超過1000，不能是負數
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,     //表示幾次要取得幾筆商品數據(保護資料庫效能)
            @RequestParam(defaultValue = "0") @Min(0) Integer offset     //表示要跳過多少筆數據

    ){
        //透過設定ProductQueryParams 將傳遞參數傳入，只需要修改ProductQueryParams 這CLASS，就不需要頻繁修改SERVICE、DAO
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得 product list
        List<Product> productList = productService.getProducts(productQueryParams);

        //取得product總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁 將資料回傳給前端(回傳json object)
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    //查詢商品中某個特定商品，"/products/{productId}"的{productId}不存在時，需要回404給前端
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        Product product = productService.getProductById(productId);

        //確認回傳值是否為空
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    //新增
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);


        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    //修改
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                @RequestBody @Valid ProductRequest productRequest){

        //檢查product是否存在
        Product product = productService.getProductById(productId);

        if(product == null){
            //404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //修改商品數據
        productService.updateProduct(productId,productRequest);

        Product updatedProduct = productService.getProductById(productId);
        //201   created
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    //刪除
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);
        //204  No content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
