package com.warehouseapi.service.ProductService;

import com.warehouseapi.model.Product;
import com.warehouseapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    /*
    Implement the business logic for the ProductService operations in this class
    Make sure to add required annotations
     */
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // to post all the product details
    // created ->201
    // badRequest ->400


    // Post product
    public ResponseEntity<Object> postProduct(Product product){
       if(productRepository.existsBySku(product.getSku())){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }else{
           try{
               Product newProduct = new Product(product.getName(),product.getDescription(),product.getVendor(),
                       product.getPrice(),product.getStocks(),product.getCurrency(),product.getImage_url(),
                       product.getSku());
               productRepository.save(newProduct);
               return ResponseEntity.status(HttpStatus.CREATED).build();
           }catch (Exception e){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
           }

       }
    }

    // to get all the product details
    // ok-> 200
    // badRequest ->400

    public ResponseEntity<Object> getProduct(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.ok(products);
        }
    }

    // to get the product with the value (pathVariable)
    // ok()-> 200
    // badRequest ->400

    public ResponseEntity<List<Product>> getSimilarVendor(String value) {
        List<Product> similarProducts = productRepository.findByVendor(value);

        if(similarProducts.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.ok(similarProducts);
        }
    }

    // To update the product with id as PathVariable and Product as object in RequestBody
    // ok->200
    // badRequest ->400

    public ResponseEntity<Object> updateProduct(@PathVariable int id,@RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent()){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            try {
                Product existingProduct = optionalProduct.get();
                existingProduct.setPrice(product.getPrice());
                existingProduct.setStocks(product.getStocks());

                Product saveProduct = productRepository.save(existingProduct);
                return ResponseEntity.ok(saveProduct);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            }
        }
    }

    // to delete the product by using id as PathVariable
    // ok->200
    // badRequest->400
    public ResponseEntity<Object> deleteProductById(@PathVariable int id){
        if(!productRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try{
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            
        }
    }

}
