package com.warehouseapi.controller;

import com.warehouseapi.model.Product;
import com.warehouseapi.service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    /*
    This controller would be responsible for the ProductController endpoints And the required annotations and
    the required annotations and create endpoints
     */

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // To add the Product details using Product object

    @PostMapping("/product/add")
    private Object postProduct(@RequestBody Product product) {
        return productService.postProduct(product);
    }

    // To get all the Product details

    @GetMapping("/product/get")
    private ResponseEntity<Object> getProduct() {
        return productService.getProduct();
    }


    // To update the product with id as pathVariable and product as object in RequestBody

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }


    // To delete the product by using id as PathVariable
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(value = "id") int id) {
        return productService.deleteProductById(id);
    }

    // To get the product items by vendor

    @GetMapping("/product/vendor")
    public ResponseEntity<List<Product>> getSimilarVendor(@RequestParam(value = "vendor") String value) {
        return productService.getSimilarVendor(value);
    }
}